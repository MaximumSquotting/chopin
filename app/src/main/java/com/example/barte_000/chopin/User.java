package com.example.barte_000.chopin;

/**
 * Created by barte_000 on 20.11.2016.
 */

public class User {
    String email;
    String password;

    User(String email, String password){
        this.email = email;
        this.password = password;
    }

    User(){
        this.email = "user1@example.com";
        this.password = "qweasdzxc";
    }
}
