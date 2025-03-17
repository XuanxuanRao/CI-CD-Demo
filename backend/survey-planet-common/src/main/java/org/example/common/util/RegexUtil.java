package org.example.common.util;

public class RegexUtil {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    private static final String PHONE_REGEX = "^1[3-9]\\d{9}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,16}$";
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{4,20}$";
    public static final String CAPTCHA_REGEX = "^[0-9]{6}$";
    public static final String URL_REGEX = "^http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?$";
    public static final String IP_REGEX = "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$";


    public static boolean isEmailValid(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isPhoneValid(String phone) {
        return phone.matches(PHONE_REGEX);
    }

    public static boolean isPasswordValid(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isUsernameValid(String username) {
        return username.matches(USERNAME_REGEX);
    }

}
