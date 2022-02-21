package com.pnf.api.service;

import com.pnf.api.request.UserLoginPostReq;
import com.pnf.api.request.UserUpdatePutReq;
import com.pnf.api.response.UserLoginPostRes;
import com.pnf.db.entity.Expert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pnf.api.request.UserRegisterPostReq;
import com.pnf.db.entity.User;
import com.pnf.db.repository.UserRepository;
import com.pnf.db.repository.UserRepositorySupport;

import java.util.Optional;
import java.util.Random;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")

public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;


	
	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		String nickname = userRegisterInfo.getNickname();
		String email = userRegisterInfo.getEmail();
		User checkNickname = userRepositorySupport.findUserByNickname(nickname);
		User checkEmail = userRepositorySupport.findUserByEmail(email);

		if (checkNickname != null || checkEmail != null)
			return null;

		if(userRegisterInfo.getIsProfessional()){
			Expert expert = new Expert();
			expert.setEmail(userRegisterInfo.getEmail());
			expert.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
			expert.setNickname(userRegisterInfo.getNickname());
			expert.setName(userRegisterInfo.getName());
			expert.setPhone(userRegisterInfo.getPhone());
			expert.setZipcode(userRegisterInfo.getZipcode());
			expert.setBaseAddress(userRegisterInfo.getBaseAddress());
			expert.setDetailAddress(userRegisterInfo.getDetailAddress());
			expert.setBusinessName(userRegisterInfo.getBusinessName());
			expert.setBusinessNumber(userRegisterInfo.getBusinessNumber());
			expert.setBusinessType(userRegisterInfo.getBusinessType());

			return userRepository.save(expert);
		}else{
			User user = new User();
			user.setEmail(userRegisterInfo.getEmail());
			// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
			user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
			user.setNickname(userRegisterInfo.getNickname());
			user.setName(userRegisterInfo.getName());
			user.setPhone(userRegisterInfo.getPhone());
			user.setZipcode(userRegisterInfo.getZipcode());
			user.setBaseAddress(userRegisterInfo.getBaseAddress());
			user.setDetailAddress(userRegisterInfo.getDetailAddress());

			return userRepository.save(user);
		}

	}

	@Override
	public boolean updateUser(UserUpdatePutReq userUpdatePutReq) {

		if (userUpdatePutReq.getNickname() != null) {
			User checkNickname = userRepository.findUserByNickname(userUpdatePutReq.getNickname());
			Expert checkNickname2 = userRepository.findExpertByNickname(userUpdatePutReq.getNickname());

			if (checkNickname != null || checkNickname2 != null)	//닉네임이 중복되면
				return false;
		}

		Expert expert = userRepository.findExpertById(userUpdatePutReq.getUserId());	//전문가가 있는지 확인

		if (expert == null) {
			User user = userRepository.findUserById(userUpdatePutReq.getUserId());
			if (user == null)
				return false;

			if (userUpdatePutReq.getPassword() != null)
				user.setPassword(passwordEncoder.encode(userUpdatePutReq.getPassword()));
			user.setNickname((userUpdatePutReq.getNickname() != null)? userUpdatePutReq.getNickname() : user.getNickname());
			user.setPhone((userUpdatePutReq.getPhone() != null)? userUpdatePutReq.getPhone() : user.getPhone());
			user.setZipcode((userUpdatePutReq.getZipcode() != null)? userUpdatePutReq.getZipcode() : user.getZipcode());
			user.setBaseAddress((userUpdatePutReq.getBaseAddress() != null)? userUpdatePutReq.getBaseAddress() : user.getBaseAddress());
			user.setDetailAddress((userUpdatePutReq.getDetailAddress() != null)? userUpdatePutReq.getDetailAddress() : user.getDetailAddress());
			user.setProfilePhoto((userUpdatePutReq.getProfilePhoto() != null)? userUpdatePutReq.getProfilePhoto() : user.getProfilePhoto());
			userRepository.save(user);
		}
		else {
			if (userUpdatePutReq.getPassword() != null)
				expert.setPassword(passwordEncoder.encode(userUpdatePutReq.getPassword()));
			expert.setNickname((userUpdatePutReq.getNickname() != null)? userUpdatePutReq.getNickname() : expert.getNickname());
			expert.setPhone((userUpdatePutReq.getPhone() != null)? userUpdatePutReq.getPhone() : expert.getPhone());
			expert.setZipcode((userUpdatePutReq.getZipcode() != null)? userUpdatePutReq.getZipcode() : expert.getZipcode());
			expert.setBaseAddress((userUpdatePutReq.getBaseAddress() != null)? userUpdatePutReq.getBaseAddress() : expert.getBaseAddress());
			expert.setDetailAddress((userUpdatePutReq.getDetailAddress() != null)? userUpdatePutReq.getDetailAddress() : expert.getDetailAddress());
			expert.setBusinessName((userUpdatePutReq.getBusinessName() != null)? userUpdatePutReq.getBusinessName() : expert.getBusinessName());
			expert.setBusinessNumber((userUpdatePutReq.getBusinessNumber() != null)? userUpdatePutReq.getBusinessNumber() : expert.getBusinessNumber());
			expert.setBusinessType((userUpdatePutReq.getBusinessType() != null)? userUpdatePutReq.getBusinessType() : expert.getBusinessType());
			expert.setProfilePhoto((userUpdatePutReq.getProfilePhoto() != null)? userUpdatePutReq.getProfilePhoto() : expert.getProfilePhoto());
			userRepository.save(expert);
		}

		return true;
	}

	@Override
	public boolean deleteUser(Long userId) {
		Expert expert = userRepository.findExpertById(userId);
		if (expert == null) {
			User user = userRepository.findUserById(userId);
			if (user == null)
				return false;

			userRepository.delete(user);
		}
		else
			userRepository.delete(expert);
		return true;
	}

	@Override
	public UserLoginPostRes loginUser(UserLoginPostReq userLoginPostReq) {
//		User user = new User();
//		user.setEmail(userLoginPostReq.getEmail());
//		user.setPassword(userLoginPostReq.getPassword());

		UserLoginPostRes userLoginPostRes = new UserLoginPostRes();

		return userLoginPostRes;
	}

	@Override
	public User getUserByUserEmail(String userEmail) {
		// 디비에 유저 정보 조회 (userEmail 를 통한 조회).
		return userRepositorySupport.findUserByEmail(userEmail);

	}

	@Override
	public Expert getExpertByUserEmail(String userEmail) {
		return userRepositorySupport.findExpertByEmail(userEmail);
	}

	@Override
	public User getUserByUserNickname(String nickname) {
		return userRepository.findUserByNickname(nickname);
	}

	@Override
	public Expert getExpertByUserNickname(String nickname) {
		return userRepository.findExpertByNickname(nickname);
	}

	@Override
	public String createEmailToken(String email) {
		User user = userRepository.findUserByEmail(email);
		if (user == null)
			return null;

		Random r = new Random();
		int leftLimit = 48; // '0'
		int rightLimit = 122; // 'z'
		int tokenSize = 15;
		String token = r.ints(leftLimit,rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i>= 97))
				.limit(tokenSize)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();

		user.setCertifiedToken(token);	//유저정보에 토큰 저장
		userRepository.save(user);
		return token;
	}


	@Override
	public boolean checkEmailToken(String email, String token) {
		User user = userRepository.findUserByEmail(email);

		if (user != null && user.getCertifiedToken() != null && user.getCertifiedToken().equals(token)) {
			user.setEmailCertified(true);
			userRepository.save(user);
			return true;
		}

		return false;
	}

}
