package com.example.proiect_apostu_gavril_1081;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Card.class, Feedback.class}, version = 1, exportSchema = false)
public abstract class UsersWithCardsDB extends RoomDatabase {
    public abstract UserDAO getUserDAO();
    public abstract CardDAO getCardDAO();
    public abstract FeedbackDAO getFeedbackDAO();
}
