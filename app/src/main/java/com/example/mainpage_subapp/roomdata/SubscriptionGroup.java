package com.example.mainpage_subapp.roomdata;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;



@Entity(tableName = "subgroup", foreignKeys = @ForeignKey(entity = Subscription.class, parentColumns = "id", childColumns = "id"))
public class SubscriptionGroup {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "group_id")
    private int group_id;


    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "start_date")
    private String start_date;

    public SubscriptionGroup(int id, String start_date) {
        this.id = id;
        this.start_date = start_date;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
}
