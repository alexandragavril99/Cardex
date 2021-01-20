package com.example.proiect_apostu_gavril_1081;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Users")
public class User implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    private int idUser;

    public int getIdUser() {
        return idUser;
    }

    private String username;
    private String password;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String birthday;

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    public User(String username, String password, String name, String surname, String phone, String email, String birthday) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        idUser = in.readInt();
        name = in.readString();
        surname = in.readString();
        phone = in.readString();
        email = in.readString();
        birthday = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeInt(idUser);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(birthday);
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}