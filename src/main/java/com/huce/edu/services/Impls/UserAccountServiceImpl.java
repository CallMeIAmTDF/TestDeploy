package com.huce.edu.services.Impls;

import com.huce.edu.entities.KeytokenEntity;
import com.huce.edu.entities.OtpEntity;
import com.huce.edu.entities.UserEntity;
import com.huce.edu.entities.VerificationcodeEntity;
import com.huce.edu.enums.RegisterEnum;
import com.huce.edu.enums.VerificationEnum;
import com.huce.edu.models.dto.ResetPasswordDto;
import com.huce.edu.models.dto.UpdateUserAccountDto;
import com.huce.edu.models.dto.UserAccountDto;
import com.huce.edu.repositories.KeyRepo;
import com.huce.edu.repositories.OtpRepo;
import com.huce.edu.repositories.UserAccountRepo;
import com.huce.edu.repositories.VerificationCodeRepo;
import com.huce.edu.security.JwtService;
import com.huce.edu.services.SendMailService;
import com.huce.edu.services.UserAccountService;
import com.huce.edu.shareds.Constants;
import com.huce.edu.utils.DateUtil;
import com.huce.edu.utils.GenerateKeyUtil;
import com.huce.edu.utils.GenerateOtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepo userRepository;
    private final OtpRepo otpRepo;
    private final VerificationCodeRepo verificationCodeRepo;
    private final KeyRepo keyRepo;

    private final JwtService jwtService;
    private final SendMailService sendMailService;
    private final PasswordEncoder passwordEncoder;

    private UserEntity createUser(UserAccountDto newUser) {
        return UserEntity.create(0, newUser.getEmail(), newUser.getName(), passwordEncoder.encode(newUser.getPassword()), 0.0, 0);
    }

    @Override
    public void save(UserEntity UserEntity) {

    }

    @Override
    public void edit(UpdateUserAccountDto updateUserAccountDto) {

    }

    @Override
    public boolean isTimeOutRequired(OtpEntity otpEntity, long ms) {
        if (otpEntity.getCode() == null) {
            return true;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = otpEntity.getExp().getTime();

        return otpRequestedTimeInMillis + ms <= currentTimeInMillis;
    }

    @Override
    public boolean isTimeOutRequired(VerificationcodeEntity VerificationcodeEntity, long ms) {
        if (VerificationcodeEntity.getCode() == null) {
            return true;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = VerificationcodeEntity.getExp().getTime();

        return otpRequestedTimeInMillis + ms <= currentTimeInMillis;
    }

    @Override
    public RegisterEnum register(UserAccountDto newUser) throws MessagingException, NoSuchAlgorithmException {
//        /* Kiểm tra xem username có tồn tại trong tbl_user chưa */
//        /* 1. Username đã tồn tại */
//        if (userRepository.existsByUsername(newUser.getUsername())) {
//            UsersEntity userByUsername = userRepository.findFirstByUsername(newUser.getUsername());
//            /* Kiểm tra xem user sở hữu username đó đã được kích hoạt chưa */
//            /* 1.1 Username chưa được kích hoạt */
//            if (!userByUsername.getStatus()) {
//                /* Kiểm tra xem email đã tồn tại trong db chưa */
//                /* 1.1.1. Email đã tồn tại trong db */
//                if (userRepository.existsByEmail(newUser.getEmail())) {
//                    UsersEntity userByEmail = userRepository.findFirstByEmail(newUser.getEmail());
//                    /*  Kiểm tra xem email đó đã được kích hoạt chưa */
//                    /* 1.1.1.1. Email chưa được kích hoạt */
//                    if (!userByEmail.getStatus()) {
//                        /* Tạo mới user và xóa 2 đối tượng có username và email trùng */
//                        // Tạo user +  gửi mail + save
//                        UsersEntity UsersEntity = createUser()(newUser);
//                        sendMailService.registerCustomer(UsersEntity);
//                        userRepository.save(UsersEntity);
//                        // xóa 2 đối tượng cũ
//                        userRepository.deleteById(userByUsername.getId());
//                        userRepository.deleteById(userByEmail.getId());
//
//                        return RegisterEnum.SUCCESS;
//                    }
//                    /* 1.1.1.2. Email đã được kích hoạt */
//                    else {
//                        /* Thông báo cho người dùng không sử dụng email này */
//
//                        return RegisterEnum.DUPLICATE_EMAIL;
//                    }
//                }
//                /* 1.1.2. Email chưa tồn tại trong db */
//                else {
//                    /* Tạo user và xóa user có username trùng */
//                    // Tạo user +  gửi mail + save
//                    UsersEntity UsersEntity = createUser()(newUser);
//                    sendMailService.registerCustomer(UsersEntity);
//                    userRepository.save(UsersEntity);
//                    // xóa 1 đối tượng cũ
//                    userRepository.deleteById(userByUsername.getId());
//
//                    return RegisterEnum.SUCCESS;
//                }
//            }
//            /* 1.2 Username đã được kích hoạt*/
//            else {
//                /* Thông báo cho người dùng không sử dụng username này */
//                return RegisterEnum.DUPLICATE_USERNAME;
//            }
//        }
//        /* 2. Username chưa tồn tại */
//        else {
        Date currentDay = DateUtil.getCurrentDay();
        String code = RandomStringUtils.randomAlphanumeric(64);
        String privateKey = GenerateKeyUtil.generate();
        String publicKey = GenerateKeyUtil.generate();


        /* Kiểm tra xem email đã có người dùng chưa */
        /* 2.1 Email đã tồn tại */
        if (userRepository.existsByEmail(newUser.getEmail())) {
            UserEntity userByEmail = userRepository.findFirstByEmail(newUser.getEmail());
            /*Kiểm tra xem email đó đã được kích hoạt chưa */
            /* 2.1.1. Email chưa được kích hoạt */
            if (userByEmail.getStatus() == 0) {


                /* Tạo mới user và xóa đối tượng có email trùng */
                // Tạo user +  gửi mail + save
                UserEntity UserEntity = createUser(newUser);
                sendMailService.registerUser(UserEntity, code);
                userRepository.save(UserEntity);
                // xóa 1 đối tượng cũ
                verificationCodeRepo.deleteById(userByEmail.getUid());
                keyRepo.deleteById(userByEmail.getUid());
                userRepository.delete(userByEmail);

                // Lấy idUser vừa thêm
                UserEntity currentUser = userRepository.findFirstByEmail(newUser.getEmail());

                /* Tạo VerificationCode và kiểm tra xem có email đó chưa*/
                VerificationcodeEntity codeByEmail = verificationCodeRepo.findFirstByEmail(newUser.getEmail());
                /* Email đã có trong bảng verificationCode thì cập nhật code với hạn mới */
                if (codeByEmail != null) {
                    verificationCodeRepo.delete(codeByEmail);
                }

                /* Tạo mới */
                VerificationcodeEntity verificationCode = VerificationcodeEntity.create(currentUser.getUid(), newUser.getEmail(), code, currentDay);
                verificationCodeRepo.save(verificationCode);

                /* Thêm vào bảng key */
                // Kiểm tra xem đã tồn tại userID trong bảng Key chưa
                KeytokenEntity keyByUserId = keyRepo.findFirstByUid(currentUser.getUid());

                if (keyByUserId != null) {
                    keyRepo.delete(keyByUserId);
                }

                KeytokenEntity newKey = KeytokenEntity.create(0, currentUser.getUid(), null, privateKey, publicKey, jwtService.generateRefreshToken(userByEmail.getEmail(), privateKey));
                keyRepo.save(newKey);

                return RegisterEnum.SUCCESS;
            }
            /* 2.1.2. Email đã được kích hoạt */
            else {
                /*Thông báo cho người dùng không sử dụng email này */
                return RegisterEnum.DUPLICATE_EMAIL;
            }
        }
        /* 2.2 Email chưa tồn tại */
        else {

            /* Tạo user mới hoàn toàn */
            // Tạo user +  gửi mail + save
            UserEntity UserEntity = createUser(newUser);
            sendMailService.registerUser(UserEntity, code);
            userRepository.save(UserEntity);

            // Lấy idUser vừa thêm
            UserEntity currentUser = userRepository.findFirstByEmail(newUser.getEmail());

            /* Tạo VerificationCode và kiểm tra xem có email đó chưa*/
            VerificationcodeEntity codeByEmail = verificationCodeRepo.findFirstByEmail(newUser.getEmail());
            /* Email đã có trong bảng verificationCode thì cập nhật code với hạn mới */
            if (codeByEmail != null) {
                verificationCodeRepo.delete(codeByEmail);
            }

            /* Tạo mới */
            VerificationcodeEntity verificationCode = VerificationcodeEntity.create(currentUser.getUid(), newUser.getEmail(), code, currentDay);
            verificationCodeRepo.save(verificationCode);

            /* Tạo mới key */
            KeytokenEntity newKey = KeytokenEntity.create(0, currentUser.getUid(), null, privateKey, publicKey, jwtService.generateRefreshToken(UserEntity.getEmail(), privateKey));
            keyRepo.save(newKey);

            return RegisterEnum.SUCCESS;
        }
    }
//    }

    @Override
    public VerificationEnum verify(String code) {
        VerificationcodeEntity userCode = verificationCodeRepo.findFirstByCode(code);

        if (userCode == null || !Objects.equals(userCode.getCode(), code)) return VerificationEnum.FAILED;
        if (isTimeOutRequired(userCode, Constants.VERIFICATION_CODE_DURATION)) return VerificationEnum.TIME_OUT;

        UserEntity userByEmail = userRepository.findFirstByEmail(userCode.getEmail());
        if (userByEmail.getStatus() == 1) return VerificationEnum.FAILED;
        userByEmail.setStatus(1);
        userRepository.save(userByEmail);

        verificationCodeRepo.delete(userCode);
        return VerificationEnum.SUCCESS;
    }

    @Override
    public void forgetPassword(int userId) {

        UserEntity user = userRepository.findFirstByUid(userId);
        OtpEntity otp = otpRepo.findFirstByUid(userId);
        String newOtp = GenerateOtpUtil.create(6);

        if (otp == null) {
            OtpEntity newOtpEntity = OtpEntity.create(userId, user.getEmail(), newOtp, 0, Constants.getCurrentDay());
            otpRepo.save(newOtpEntity);
        } else {
            otp.setCode(newOtp);
            otp.setExp(Constants.getCurrentDay());
            otp.setFailattempts(0);

            otpRepo.save(otp);
        }
        sendMailService.forgetPasswordUser(user, newOtp);
    }

    @Override
    public VerificationEnum checkOtp(ResetPasswordDto resetPasswordDto) {
        UserEntity userByEmail = userRepository.findFirstByEmail(resetPasswordDto.getEmail());
        if (userByEmail == null) return VerificationEnum.NOT_FOUND;

        OtpEntity otpByUserId = otpRepo.findFirstByUid(userByEmail.getUid());
        if (otpByUserId.getFailattempts() >= 5) return VerificationEnum.FAIL_ATTEMPT;
        if (Objects.equals(otpByUserId.getCode(), resetPasswordDto.getOtp()) && Objects.equals(userByEmail.getEmail(), resetPasswordDto.getEmail())) {
            if (isTimeOutRequired(otpByUserId, Constants.OTP_VALID_DURATION_5P)) {
                otpByUserId.setFailattempts(otpByUserId.getFailattempts() + 1);
                otpRepo.save(otpByUserId);
                return VerificationEnum.TIME_OUT;
            }
            // cập nhật pass
            userByEmail.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
            userRepository.save(userByEmail);
            // xoá otp
            otpRepo.delete(otpByUserId);
            return VerificationEnum.SUCCESS;
        }
        otpByUserId.setFailattempts(otpByUserId.getFailattempts() + 1);
        otpRepo.save(otpByUserId);
        return VerificationEnum.FAILED;
    }
}
