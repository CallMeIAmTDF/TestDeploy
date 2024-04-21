package com.huce.edu.controllers;

import com.huce.edu.entities.AdminsEntity;
import com.huce.edu.entities.KeytokenEntity;
import com.huce.edu.models.ApiResult;
import com.huce.edu.models.dto.AdminInfo;
import com.huce.edu.models.dto.AuthRequest;
import com.huce.edu.models.dto.UserInfo;
import com.huce.edu.models.response.LoginResponse;
import com.huce.edu.models.response.TokenResponse;
import com.huce.edu.repositories.AdminsRepo;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/admin")
@RequiredArgsConstructor
public class AdminController {


    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final KeyRepo keyRepo;
    private final UserAccountRepo userAccountRepo;
    private final AdminsRepo adminsRepo;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResult<List<AdminInfo>>> getAll() {
        List<AdminsEntity> listAdminEntity = adminsRepo.findAll();
        List<AdminInfo> infoList = new ArrayList<>();
        for (AdminsEntity admin : listAdminEntity) {
            infoList.add(AdminInfo.create(admin.getAid(), admin.getEmail(), admin.getName(), admin.getRole()));
        }
        ApiResult<List<AdminInfo>> result = ApiResult.create(HttpStatus.OK, "Lấy thành công danh sách Admin.", infoList);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResult<LoginResponse>> login(@RequestBody AuthRequest authRequest) {
        ApiResult<LoginResponse> result = null;
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                AdminsEntity admin = adminsRepo.findFirstByEmail(authRequest.getEmail());
                if (admin != null) {
                    String privateKey;
                    String publicKey;
                    TokenResponse tokens;
                    KeytokenEntity keyByUserId = keyRepo.findFirstByAid(admin.getAid());

                    // Tạo mơi
                    if (keyByUserId == null) {
                        publicKey = GenerateKeyUtil.generate();
                        privateKey = GenerateKeyUtil.generate();
                        KeytokenEntity newKey = KeytokenEntity.create(0, null, admin.getAid(), privateKey, publicKey, null);
                        keyRepo.save(newKey);
                        tokens = TokenResponse.create(jwtService.generateAccessToken(authRequest.getEmail(), publicKey), jwtService.generateRefreshToken(authRequest.getEmail(), privateKey));
                    } else {
                        // lấy key ra
                        KeytokenEntity keyByUser = keyRepo.findFirstByAid(admin.getAid());
                        tokens = TokenResponse.create(jwtService.generateAccessToken(authRequest.getEmail(), keyByUser.getPublickey()), jwtService.generateRefreshToken(authRequest.getEmail(), keyByUser.getPrivatekey()));
                    }
                    UserInfo userInfo = UserInfo.create(admin.getAid(), admin.getEmail(), admin.getName());

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

    @GetMapping("/verifyRefreshToken")
    public ResponseEntity<ApiResult<TokenResponse>> verifyUser(@RequestParam(name = "token") String token) {
        ApiResult<TokenResponse> result = null;
        String username = BearerTokenUtil.getUserName(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        AdminsEntity admin = adminsRepo.findFirstByEmail(username);
        if (admin == null) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Không tồn tại admin!!", null);
            return ResponseEntity.ok(result);
        }
        try {
            KeytokenEntity keyByAdmin = keyRepo.findFirstByAid(admin.getAid());
            if (jwtService.validateToken(token, keyByAdmin.getPrivatekey(), userDetails)) {
                // Tạo lại AccessToken và RefreshToken
                TokenResponse tokens = TokenResponse.create(jwtService.generateAccessToken(admin.getEmail(), keyByAdmin.getPublickey()), jwtService.generateRefreshToken(admin.getEmail(), keyByAdmin.getPrivatekey()));
//                keyByAdmin.setRefreshtoken(refreshToken);
                keyRepo.save(keyByAdmin);
                result = ApiResult.create(HttpStatus.OK, "Cấp lại AccessToken và RefreshToken thành công!!", tokens);
            }
        } catch (Exception ex) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "RefreshToken sai!", null);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResult<?>> add(@RequestBody AdminsEntity adminDto) {
        ApiResult<?> result;

        if (adminsRepo.existsByEmail(adminDto.getEmail()) || userAccountRepo.existsByEmail(adminDto.getEmail())) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Email đã được dùng rồi!!!", null);
            return ResponseEntity.ok(result);
        }

        AdminsEntity newAdmin = AdminsEntity.create(0, adminDto.getEmail(), adminDto.getName(), passwordEncoder.encode(adminDto.getPassword()), "ADMIN");
        adminsRepo.save(newAdmin);

        AdminsEntity adminsEntity = adminsRepo.findFirstByEmail(adminDto.getEmail());
        AdminInfo adminInfo = AdminInfo.create(adminsEntity.getAid(), adminsEntity.getEmail(), adminsEntity.getName(), adminsEntity.getRole());

        result = ApiResult.create(HttpStatus.OK, "Thêm Admin thành công.", adminInfo);
        return ResponseEntity.ok(result);
    }
}
