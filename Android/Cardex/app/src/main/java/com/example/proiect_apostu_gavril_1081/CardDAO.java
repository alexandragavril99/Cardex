package com.example.proiect_apostu_gavril_1081;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CardDAO {

    @Insert
    public void insertCard(Card card);

    @Query("SELECT * FROM Cards WHERE id_fkuser =:id_fkUser")
    public List<Card> getCardByUserId(int id_fkUser);

    @Query("SELECT * FROM Cards WHERE category =:category AND id_fkuser =:id_fkUser")
    public List<Card> getCardByCategoryAndUserId(String category, int id_fkUser);

    @Query("SELECT * FROM Cards WHERE idCard =:idCard AND id_fkuser =:id_fkuser")
    public Card getCardByIdCard(int idCard, int id_fkuser);

    @Delete
    public void deleteCard(Card card);

    @Update
    public void updateCard(Card card);
}
