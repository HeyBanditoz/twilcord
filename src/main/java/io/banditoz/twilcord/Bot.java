package io.banditoz.twilcord;

import javax.security.auth.login.LoginException;

public class Bot {
    public static void main(String[] args) throws LoginException, InterruptedException {
        Twilcord.setupBot();
    }
}
