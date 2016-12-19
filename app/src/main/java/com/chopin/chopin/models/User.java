package com.chopin.chopin.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
    private Integer id;
    private String email;
    private String password;
    private String password_confirmation;
    private String name;
    private String address;
    private String confirm_success_url = "/";

    protected User(Parcel in) {
        id = in.readInt();
        email = in.readString();
        password = in.readString();
        password_confirmation = in.readString();
        name = in.readString();
        address = in.readString();
        confirm_success_url = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getConfirm_success_url() {
        return confirm_success_url;
    }

    public void setConfirm_success_url(String confirm_success_url) {
        this.confirm_success_url = confirm_success_url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(Integer id, String email, String password, String name, String address) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.password_confirmation = password;
        this.name = name;
        this.address = address;
    }
    public User(String name, String email, String address) {
        this.id = null;
        this.email = email;
        this.password = null;
        this.password_confirmation = password;
        this.name = name;
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{
                this.id.toString(),
                this.name,
                this.email
        });
    }
}
