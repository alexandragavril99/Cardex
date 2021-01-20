package com.example.proiect_apostu_gavril_1081;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Feedbacks", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "idUser",
        childColumns = "fk_idUser"), indices = @Index("fk_idUser"))
public class Feedback {

    @PrimaryKey(autoGenerate = true)
    private int idFeedback;
    private int fk_idUser;
    private float noStars;
    private boolean recommend;

    public int getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(int idFeedback) {
        this.idFeedback = idFeedback;
    }

    public int getFk_idUser() {
        return fk_idUser;
    }

    public void setFk_idUser(int fk_idUser) {
        this.fk_idUser = fk_idUser;
    }

    public float getNoStars() {
        return noStars;
    }

    public void setNoStars(float noStars) {
        this.noStars = noStars;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Feedback(int fk_idUser, float noStars, boolean recommend) {
        this.fk_idUser = fk_idUser;
        this.noStars = noStars;
        this.recommend = recommend;
    }

    @Ignore
    public Feedback() {
        this.fk_idUser = 0;
        this.noStars = 0;
        this.recommend = true;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "idFeedback=" + idFeedback +
                ", fk_idUser=" + fk_idUser +
                ", noStars=" + noStars +
                ", recommend=" + recommend +
                '}';
    }
}
