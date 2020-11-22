package com.example.mainpage_subapp.roomdata;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Room;


@Database(entities = {Subscription.class}, version = 1)
public abstract class SubscriptionDatabase extends RoomDatabase{

    //okay, buckle up because we are gonna be defining a buncha weird stuff here

    //First, create an instance of the SubscriptionDatabase object called 'instance' by default
    //this guy basically is used to access and control all things we do with the database
    private static SubscriptionDatabase instance;

    //this is a method that allows us to access all of our Dao methods - insert, update, delete etc etc
    public abstract SubscriptionDao subscriptionDao();

    //create a static, synchronised (not s the other letter!) SubscriptionDatabase function called getInstance
    //this is used to interact with the private static instance object
    //- it is synced so that we dont have two threads working on our database at the same time - kinda cool!

    public static synchronized SubscriptionDatabase getInstance(Context context){
        //we only create an instance of our database if it doesnlt already exist
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SubscriptionDatabase.class, "subscription_database")
                    .fallbackToDestructiveMigration() //this has to do with version control and android APIs
                    .build();
        }
        //otherwise, we just return it
        return instance;
    }

}
