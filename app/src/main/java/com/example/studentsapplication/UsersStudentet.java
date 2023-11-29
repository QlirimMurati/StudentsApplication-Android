package com.example.studentsapplication;

public class UsersStudentet {
    String name,email,password,salt,prioriteti;
    int username;

    public String getNameUsers() {
        return name;
    }

    public String getEmailUsers() {
        return email;
    }

    public String getPasswordUsers() {
        return password;
    }

    public String getSaltUsers() {
        return salt;
    }

    public String getPrioritetiUsers() {
        return prioriteti;
    }

    public int getUsernameUsers() {
        return username;
    }


    public void setNameUsers(String name) {
        this.name = name;
    }

    public void setEmailUsers(String email) {
        this.email = email;
    }

    public void setPasswordUsers(String password) {
        this.password = password;
    }

    public void setSaltUsers(String salt) {
        this.salt = salt;
    }

    public void setUsernameUsers(int username) {
        this.username = username;
    }

    public void setPrioritetiUsers(String prioriteti) {
        this.prioriteti = prioriteti;
    }
}
