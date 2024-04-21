package com.huce.edu.controllers;

import com.huce.edu.entities.KeytokenEntity;
import com.huce.edu.entities.UserEntity;
import com.huce.edu.models.ApiResult;
import com.huce.edu.models.dto.AuthRequest;
import com.huce.edu.models.dto.UserInfo;
import com.huce.edu.models.response.LoginResponse;
import com.huce.edu.models.response.TokenResponse;
import com.huce.edu.repositories.KeyRepo;
import com.huce.edu.repositories.UserAccountRepo;
import com.huce.edu.security.JwtService;
import com.huce.edu.utils.BearerTokenUtil;
import com.huce.edu.utils.GenerateKeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class AuthController {


    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserAccountRepo userAccountRepo;
    private final KeyRepo keyRepo;

    @GetMapping("/verifyRefreshToken")
    public ResponseEntity<ApiResult<TokenResponse>> verifyUser(@RequestParam(name = "token") String token) {
        ApiResult<TokenResponse> result = null;
        String username = BearerTokenUtil.getUserName(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UserEntity user = userAccountRepo.findFirstByEmail(username);
        if (user == null) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Không tồn tại user!!", null);
            return ResponseEntity.ok(result);
        }
        try {
            KeytokenEntity keyByUser = keyRepo.findFirstByUid(user.getUid());
            if (jwtService.validateToken(token, keyByUser.getPrivatekey(), userDetails)) {
                // Tạo lại AccessToken và RefreshToken
                TokenResponse tokens = TokenResponse.create(
                        jwtService.generateAccessToken(user.getEmail(), keyByUser.getPublickey()),
                        jwtService.generateRefreshToken(user.getEmail(), keyByUser.getPrivatekey())
                );
//                keyByUser.setRefreshtoken(refreshToken);
                keyRepo.save(keyByUser);
                result = ApiResult.create(HttpStatus.OK, "Cấp lại AccessToken và RefreshToken thành công!!", tokens);
            }
        } catch (Exception ex) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "RefreshToken sai!", null);
        }
        return ResponseEntity.ok(result);
    }


    @PostMapping("/sign-in")
    public ResponseEntity<ApiResult<LoginResponse>> login(@RequestBody AuthRequest authRequest) {

        ApiResult<LoginResponse> result = null;
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                UserEntity user = userAccountRepo.findFirstByEmail(authRequest.getEmail());
                if (user != null) {
                    String privateKey;
                    String publicKey;
                    TokenResponse tokens;
                    KeytokenEntity keyByUser = keyRepo.findFirstByUid(user.getUid());

                    // Tạo mơi
                    if (keyByUser == null) {
                        publicKey = GenerateKeyUtil.generate();
                        privateKey = GenerateKeyUtil.generate();
                        KeytokenEntity newKey = KeytokenEntity.create(
                                0,
                                user.getUid(),
                                null,
                                privateKey,
                                publicKey,
                                null
                        );
                        keyRepo.save(newKey);
                        tokens = TokenResponse.create(
                                jwtService.generateAccessToken(authRequest.getEmail(), publicKey),
                                jwtService.generateRefreshToken(authRequest.getEmail(), privateKey)
                        );
                    } else {
                        // lấy key ra
                        KeytokenEntity keyByAdmin = keyRepo.findFirstByUid(user.getUid());
                        tokens = TokenResponse.create(
                                jwtService.generateAccessToken(authRequest.getEmail(), keyByAdmin.getPublickey()),
                                jwtService.generateRefreshToken(authRequest.getEmail(), keyByAdmin.getPrivatekey())
                        );
                    }
                    UserInfo userInfo = UserInfo.create(user.getUid(), user.getEmail(), user.getName());

                    LoginResponse loginResponse = LoginResponse.create(userInfo, tokens);

                    result = ApiResult.create(HttpStatus.OK, "Đăng nhập thành công!!", loginResponse);
                    return ResponseEntity.ok(result);
                }
                result = ApiResult.create(HttpStatus.BAD_REQUEST, "tài khoản chưa được kích hoạt!!", null);
            }
        } catch (Exception ex) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Sai tên đăng nhập hoặc mật khẩu!!", null);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(result);
    }


//    @GetMapping("/sign-out")
//    public ResponseEntity<ApiResult<?>> logout(HttpServletRequest request) {
//        ApiResult<?> result = null;
//        String token = BearerTokenUtil.getToken(request);
//        String username = BearerTokenUtil.getUserName(request);
//
//        if (username != null) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            UserEntity user = userAccountRepo.findFirstByEmail(username);
//            KeytokenEntity keyByUser = keyRepo.findFirstByUid(user.getUid());
//            if (jwtService.validateToken(token, keyByUser.getPublickey(), userDetails)) {
//                keyRepo.delete(keyByUser);
//                result = ApiResult.create(HttpStatus.OK, "logout Success!!", username);
//            } else {
//                result = ApiResult.create(HttpStatus.BAD_REQUEST, "Token không đúng!!", username);
//            }
//        }
//
//        return ResponseEntity.ok(result);
//    }
}
