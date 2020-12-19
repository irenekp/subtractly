package com.example.mainpage_subapp.roomdata;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

//, foreignKeys = @ForeignKey(entity = Subscription.class, parentColumns = "id", childColumns = "subid", onDelete = CASCADE)
@Entity(tableName = "subgroup")
public class SubscriptionGroup implements Parcelable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private int user_id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "subid")
    private String subid;


    public SubscriptionGroup(String username, String subid) {
        this.username = username;
        this.subid = subid;
    }

    public String getUsername() {
        return username;
    }

    public String getSubid() {
        return subid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(user_id);
        parcel.writeString(username);
        parcel.writeString(subid);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SubscriptionGroup createFromParcel(Parcel in) {
            return new SubscriptionGroup(in);
        }

        public SubscriptionGroup[] newArray(int size) {
            return new SubscriptionGroup[size];
        }

    };

    private SubscriptionGroup(Parcel in) {
        user_id = in.readInt();
        username = in.readString();
        subid = in.readString();
    }
}
