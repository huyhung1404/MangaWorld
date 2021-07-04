package com.example.mangaworld.object;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Password {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public Password(String oldPassword, String newPassword, String confirmPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    private String otp;
    private String email;
    private String password;

    public Password(String otp, String email, String password, boolean isResetPassword) {
        if (!isResetPassword) {
            this.otp = otp;
            this.email = email;
            return;
        }
        this.email = email;
        this.password = password;
    }

}
