package com.huce.edu.shareds;

import java.util.Date;

public class Constants {

    public static final String DELETE_SUCCESS = "Xoá thành công rồi nhé!";
    public static final String UPDATE_SUCCESS = "Cập nhật thành công rồi nhé!";
    public static final String RESTORE_SUCCESS = "Khôi phục thành công rồi nhé!";

    public static final String REQUIRE_TYPE = "`{1}` phải là dạng `{0}`";

    public static final String DUPLICATE_FIELD = "Trường `{0}` của bảng `{1}` đã tồn tại rồi!";

    /* Add Data */
    public static final String SAVE_DATA_SUCCESS = "Dữ liệu đã được lưu thành công.";
    public static final String DUPLICATE_ERROR_ID = "Id có giá trị ''{0}'' đã được sử dụng. Vui lòng sử dụng Id khác";
    /* Register */
    public static final String REGISTER_SUCCESS = "Đăng kí thành công.";
    public static final String CONFIRM_EMAIL = "Vui lòng hãy vào email vừa đăng kí để xác nhận!";

    public static final String DUPLICATE_ERROR_EMAIL = "Email có địa chỉ ''{0}'' đã được đăng ký rồi. Vui lòng dùng Email khác để đăng ký! ";
    public static final String DUPLICATE_ERROR_USERNAME = "Username có giá trị ''{0}'' đã được đăng ký rồi. Vui lòng dùng Username khác để đăng ký! ";

    /* OTP */

    public static final String OTP_SUCCESS = "Otp chính xác ^.^ Bạn đã đổi mật khẩu thành công. ";
    public static final String OTP_FAILED = "Otp không đúng hãy thử lại :((  ";
    public static final String OTP_TIME_OUT = "Otp đã hết hạn :(( Bạn hãy yêu cầu gửi lại otp nhé.";
    public static final String OTP_COUNT_FAIL_ATTEMPT = "Bạn đã nhập sai quá 5 lần. Hãy yêu cầu gửi lại Otp sau 5p nhé!";
    public static final String OTP_NOT_FOUND_USER = "Không tìm thấy người dùng yêu cầu quên mật khẩu!";


    /* Mail */
    public static final long OTP_VALID_DURATION_1P = 60 * 1000;
    public static final long OTP_VALID_DURATION_5P = 5 * 60 * 1000;

    public static final long VERIFICATION_CODE_DURATION = 5 * 60 * 1000;

    public static final String URL_VERIFICATION_CUSTOMER = "http://localhost:8080/api/users/register/verify?code=";

    /* JWT */

    public static final long ACCESS_TOKEN_EXP = 24 * 60 * 60 * 1000; // 1 ngày
    public static final long REFRESH_TOKEN_EXP = 30L * 24 * 60 * 60 * 1000; // 1 tháng

    // Notification



    public static final String message = "message";




    public final static class SEND_MAIL_SUBJECT {
        public final static String USER_REGISTER = "ĐƯỜNG DẪN XÁC NHẬN THÔNG TIN NGƯỜI DÙNG ĐĂNG KÝ";
        public final static String USER_FORGET_PASSWORD = "MÃ XÁC NHẬN LẤY LẠI TÀI KHOẢN NGƯỜI DÙNG";
        public static final String REQUEST_JOIN_FAMILY_TREE = "Yêu cầu tham gia sơ đồ {0}";
    }
    public final static class TEMPLATE_FILE_NAME {
        public final static String USER_FORGET_PASSWORD = "user_forget_password_email";
        public final static String VERIFY_USER = "verify_user_email";
        public static final String REQUEST_JOIN_FAMILY_TREE = "request_join_family";
    }



    /* Validate */
    public static final String INVALID_DATA_FIELD = "Dữ liệu không hợp lệ!";
    public static final String INVALID_EMAIL = "Email không hợp lệ!";
    public static final String INVALID_NOTNULL = "Must not be null!";
    public static final String INVALID_EMPTY = "Must not be empty!";
    public static final String INVALID_FILE_IMAGE = "Ảnh phải ở định dạng png hoặc jpg";
    public static final String INVALID_PASSWORD_MIN_LENGTH = "Độ dài mật khẩu phải từ 8 đến 20 kí tự";
    public static final String INVALID_USERNAME_MIN_LENGTH = "Độ dài username phải trong khoảng 6 đến 20 kí tự";
    public static final String INVALID_BIRTHDAY = "Ngày sinh không hợp lệ!";
    public static Double TOKEN_REMAIN = 50000000.0;
    public static Double REWARD_PER = 0.00000078;
    public static final int ANSWER_BONUS_AMOUNT = 10;

    /* Message */
//    public static final String


    /* Function */
    public static Date getCurrentDay(){
        Date currentDate = new Date();
        return new Date(currentDate.getTime());
    }

    /* Regex */
    public static final String REGEX_URL_IMAGE = "(https?:\\/\\/.*\\.(?:png|jpg))";

}
