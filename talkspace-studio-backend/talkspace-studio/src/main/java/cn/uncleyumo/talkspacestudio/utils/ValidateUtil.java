package cn.uncleyumo.talkspacestudio.utils;

/**
 * @author uncle_yumo
 * @fileName ValidateUtil
 * @createDate 2025/3/12 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

public class ValidateUtil {
    public static boolean validateUsernameAndPassword(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (username.length() < 8 || username.length() > 20) {
            throw new IllegalArgumentException("用户名长度必须在8-20之间");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (password.length() < 8 || password.length() > 20) {
            throw new IllegalArgumentException("密码长度必须在8-20之间");
        }
        return true;
    }
}
