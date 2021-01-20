package com.example.proiect_apostu_gavril_1081;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    public void insertUser(User user);

    @Query("SELECT * FROM Users WHERE username =:username AND password =:password")
    public User getUserByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM Users WHERE idUser =:idUser")
    public User getUserById(int idUser);

    @Query("SELECT * FROM Users WHERE email =:email")
    public User getUserByEmail(String email);

    @Query("SELECT password FROM Users WHERE email =:email")
    public String getPasswordByEmail(String email);

    @Delete
    public void deleteUser(User user);

    @Update
    public void updateUser(User user);
}
