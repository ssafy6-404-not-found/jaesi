package com.pnf.api.service;

import com.pnf.api.request.UserLoginPostReq;
import com.pnf.api.request.UserRegisterPostReq;
import com.pnf.api.request.UserUpdatePutReq;
import com.pnf.api.response.UserLoginPostRes;
import com.pnf.db.entity.Expert;
import com.pnf.db.entity.User;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	boolean updateUser(UserUpdatePutReq userUpdatePutReq);
	boolean deleteUser(Long userId);
	UserLoginPostRes loginUser(UserLoginPostReq userLoginPostReq);
	User getUserByUserEmail(String userEmail);
	Expert getExpertByUserEmail(String userEmail);
	User getUserByUserNickname(String nickname);
	Expert getExpertByUserNickname(String nickname);
	String createEmailToken(String email);
	boolean checkEmailToken(String email, String token);

}
