package com.example.proiect_apostu_gavril_1081;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Cards", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "idUser",
        childColumns = "id_fkuser"), indices = @Index("id_fkuser"))
public class Card implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int idCard;


    private String storeName;
    private String cardNumber;
    private String category;
    private int id_fkuser;
    private int idJSONObject;

    public int getIdJSONObject() {
        return idJSONObject;
    }

    public void setIdJSONObject(int idJSONObject) {
        this.idJSONObject = idJSONObject;
    }


    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Card(String storeName, String cardNumber, String category, int id_fkuser, int idJSONObject) {
        this.storeName = storeName;
        this.cardNumber = cardNumber;
        this.category = category;
        this.id_fkuser = id_fkuser;
        this.idJSONObject = idJSONObject;
    }

    public int getId_fkuser() {
        return id_fkuser;
    }

    public void setId_fkuser(int id_fkuser) {
        this.id_fkuser = id_fkuser;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "Card{" +
                "idCard=" + idCard +
                ", storeName='" + storeName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", category='" + category + '\'' +
                ", id_fkuser=" + id_fkuser +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(storeName);
        dest.writeString(cardNumber);
        dest.writeInt(idCard);
        dest.writeInt(id_fkuser);
        dest.writeString(category);
    }




    protected Card(Parcel in) {
        storeName = in.readString();
        cardNumber = in.readString();
        idCard = in.readInt();
        id_fkuser = in.readInt();
        category = in.readString();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
}
