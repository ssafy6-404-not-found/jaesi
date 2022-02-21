package com.pnf.api.controller;

import com.pnf.api.request.UserEmailCheckReq;
import com.pnf.api.request.UserLoginPostReq;
import com.pnf.api.request.UserUpdatePutReq;
import com.pnf.api.response.*;
import com.pnf.api.service.AsyncService;
import com.pnf.common.auth.UserDetails;
import com.pnf.common.util.JwtTokenUtil;
import com.pnf.db.entity.Expert;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.pnf.api.request.UserRegisterPostReq;
import com.pnf.api.service.UserService;
import com.pnf.db.entity.User;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@Autowired
	AsyncService asyncService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>유저정보</strong>를 통해 회원가입 한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 402, message = "닉네임이 중복되거나 이메일이 중복됨"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {

		User user = userService.createUser(registerInfo);
		if (user == null)
			return ResponseEntity.status(402).body(BaseResponseBody.of(402, "Dupliacate"));

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<UserLoginPostRes> login(@RequestBody @ApiParam(value="로그인 정보", required = true) UserLoginPostReq loginInfo) {
		String userEmail = loginInfo.getEmail();
		String password = loginInfo.getPassword();

		Expert expert = userService.getExpertByUserEmail(userEmail);
		if (expert == null) {	//전문가가 아니면
			User user = userService.getUserByUserEmail(userEmail);
			if (user == null)	//유저 정보를 찾을 수 없음

				return ResponseEntity.status(404).body(UserLoginPostRes.of(404,"User Not Found",null,null));

			// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
			if(passwordEncoder.matches(password, user.getPassword())) {
				// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
				return ResponseEntity.status(200).body(UserLoginPostRes.of(200,"Success",JwtTokenUtil.getToken(userEmail),user));
			}
			// 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
			return ResponseEntity.status(401).body(UserLoginPostRes.of(401,"Invalid Password",null,null));
		} else {
			if(passwordEncoder.matches(password, expert.getPassword())) {
				// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
				return ResponseEntity.status(200).body(UserLoginPostRes.of(200,"Success",JwtTokenUtil.getToken(userEmail),expert));
			}
			// 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
			return ResponseEntity.status(401).body(UserLoginPostRes.of(401,"Invalid Password",null,null));
		}
	}

	@GetMapping("/email-check/{email}")
	@ApiOperation(value = "이메일 중복 확인", notes = "이메일이 이미 등록되어 있는지 확인한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "확인성공", response = UserDataCheckRes.class)
	})
	public ResponseEntity<? extends BaseResponseBody> checkUsingEmail(
			@PathVariable("email") @ApiParam(value="중복확인할 이메일", required = true) String email) {

		User user = userService.getUserByUserEmail(email);
		Expert expert = userService.getExpertByUserEmail(email);
		if (user == null && expert == null)
			return ResponseEntity.status(200).body(UserDataCheckRes.of(200, "Success", false));

		return ResponseEntity.status(200).body(UserDataCheckRes.of(200, "Success", true));
	}

	@GetMapping("/nickname-check/{nickname}")
	@ApiOperation(value = "닉네임 중복 확인", notes = "닉네임이 이미 등록되어 있는지 확인한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "확인성공", response = UserDataCheckRes.class)
	})
	public ResponseEntity<? extends BaseResponseBody> checkUsingNickname(
			@PathVariable("nickname") @ApiParam(value="중복확인할 닉네임", required = true) String nickname) {

		User user = userService.getUserByUserNickname(nickname);
		Expert expert = userService.getExpertByUserNickname(nickname);
		if (user == null && expert == null)
			return ResponseEntity.status(200).body(UserDataCheckRes.of(200, "Success", false));

		return ResponseEntity.status(200).body(UserDataCheckRes.of(200, "Success", true));
	}

	@GetMapping("/email-token-send/{email}")
	@ApiOperation(value = "이메일 인증번호 전송", notes = "해당 이메일로 인증번호를 전송한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "전송성공", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "회원정보 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버에러", response = BaseResponseBody.class)
	})
	public ResponseEntity<? extends BaseResponseBody> sendEmailToken(
			@PathVariable("email") @ApiParam(value="인증할 이메일", required = true) String email) {
		String token = userService.createEmailToken(email);
		if (token == null)
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "User Not Found"));
		else {
			asyncService.sendEmail(email,token);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}

	}

	@PostMapping("/email-token-check")
	@ApiOperation(value = "이메일 인증번호 확인", notes = "해당 이메일로 보낸 인증번호와 서버에 저장된 인증번호 비교")
	@ApiResponses({
			@ApiResponse(code = 200, message = "인증성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증실패", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버에러", response = BaseResponseBody.class)
	})
	public ResponseEntity<? extends BaseResponseBody> checkEmailToken(
			@RequestBody @ApiParam(value="인증할 이메일", required = true)UserEmailCheckReq userEmailCheckReq) {

		String email = userEmailCheckReq.getEmail();
		String token = userEmailCheckReq.getToken();

		if (userService.checkEmailToken(email,token))
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		else
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Invalid Token"));
	}

	@GetMapping("/{email}")
	@ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
	@ApiOperation(value = "회원정보 가져오기 - jwt", notes = "로그인한 회원 본인의 정보를 가져온다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 403, message = "jwt 토큰 없음"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<UserGetRes> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		if (authentication == null)
			return ResponseEntity.status(403).body(UserGetRes.of(403,"JWT token not found",null));

		UserDetails userDetails = (UserDetails)authentication.getDetails();
		String userEmail = userDetails.getUser().getEmail();
		Expert expert = userService.getExpertByUserEmail(userEmail);
		if (expert == null) {
			User user = userService.getUserByUserEmail(userEmail);
			if (user == null)
				return ResponseEntity.status(404).body(UserGetRes.of(404,"user not found", null));
			else
				return ResponseEntity.status(200).body(UserGetRes.of(200,"Success", user));
		}

		return ResponseEntity.status(200).body(ExpertGetRes.of(200,"Success", expert));
	}

	@PutMapping()
	@ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
	@ApiOperation(value = "회원정보 수정 - jwt", notes = "입력받는 정보를 통해 회원정보를 수정한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 402, message = "업데이트 실패"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> updateUserInfo(
			@RequestBody @ApiParam(value="수정한 회원정보", required = true) UserUpdatePutReq userUpdatePutReq) {

		boolean isSuccess = userService.updateUser(userUpdatePutReq);
		if (isSuccess)
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));

		return ResponseEntity.status(402).body(BaseResponseBody.of(402, "Fail"));
	}

	@DeleteMapping()
	@ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
	@ApiOperation(value = "회원탈퇴 - jwt", notes = "입력받는 정보를 통해 회원정보를 수정한다. 헤더에 JWT토큰만 보내주면 삭제가능 회원삭제 이후엔 jwt토큰도 프론트쪽에서 제거해주는게 안전함")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 402, message = "회원이 존재하지 않음"),
			@ApiResponse(code = 403, message = "jwt 토큰 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> deleteUserInfo(
			@ApiIgnore Authentication authentication) {
		if (authentication == null)
			return ResponseEntity.status(403).body(BaseResponseBody.of(403,"JWT token not found"));

		UserDetails userDetails = (UserDetails)authentication.getDetails();
		Long userId = userDetails.getUser().getId();
		boolean isSuccess = userService.deleteUser(userId);
		if (isSuccess)
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));

		return ResponseEntity.status(402).body(BaseResponseBody.of(402, "Fail"));
	}
}
