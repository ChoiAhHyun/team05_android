package com.yapp14th.yappapp.common;

public interface Constant {

    String PASSWORD_PATTERN = "^(?=.*[a-zA-Z]+)(?=.*[0-9]+).{6,12}$";

    String EMAIL_PATTERN = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

    interface Preference {
        String CONFIG_USER_AUTOLOGIN = "AUTO_LOGIN";
        String CONFIG_USER_USERNAME = "user.username";
        String CONFIG_USER_PASSWORD = "user.password";
    }
}
