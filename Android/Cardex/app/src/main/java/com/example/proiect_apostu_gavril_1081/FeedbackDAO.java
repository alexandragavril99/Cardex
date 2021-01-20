package com.example.proiect_apostu_gavril_1081;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FeedbackDAO {

    @Insert
    public void insertFeedback(Feedback feedback);

    @Query("SELECT * FROM Feedbacks WHERE fk_idUser =:fk_idUser")
    public List<Feedback> getFeedbackByUserId(int fk_idUser);

    @Query("SELECT idFeedback FROM feedbacks WHERE fk_idUser =:fk_idUser AND noStars =:noStars AND recommend =:recommend")
    public int getIdFeedbackByAll(int fk_idUser, float noStars, boolean recommend);

    @Delete
    public void deleteFeedback(Feedback feedback);

}
