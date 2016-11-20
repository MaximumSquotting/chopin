package com.example.barte_000.chopin;

/**
 * Created by barte_000 on 20.11.2016.
 */

public class User {
    int id;
    String email;
    String password;
    String name;

    User(String email, String password){
        this.email = email;
        this.password = password;
    }
    User(int id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }
    User(int id, String email, String password, String name){
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }


    User(){
        this.email = "user1@example.com";
        this.password = "qweasdzxc";
    }
}
