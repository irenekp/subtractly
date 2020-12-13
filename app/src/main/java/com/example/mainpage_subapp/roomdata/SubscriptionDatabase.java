package com.example.mainpage_subapp.roomdata;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mainpage_subapp.R;
import com.example.mainpage_subapp.ui.home.SubscriptionDetails;

import java.util.Date;


@Database(entities = {Subscription.class, SubscriptionGroup.class}, version = 1)
public abstract class SubscriptionDatabase extends RoomDatabase {

    //okay, buckle up because we are gonna be defining a buncha weird stuff here

    //First, create an instance of the SubscriptionDatabase object called 'instance' by default
    //this guy basically is used to access and control all things we do with the database
    private static SubscriptionDatabase instance;

    //this is a method that allows us to access all of our Dao methods - insert, update, delete etc etc
    public abstract SubscriptionDao subscriptionDao();

    //create a static, synchronised (not s the other letter!) SubscriptionDatabase function called getInstance
    //this is used to interact with the private static instance object
    //- it is synced so that we dont have two threads working on our database at the same time - kinda cool!

    public static synchronized SubscriptionDatabase getInstance(Context context) {
        //we only create an instance of our database if it doesn't already exist
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SubscriptionDatabase.class, "subscription_database")
                    .fallbackToDestructiveMigration() //this has to do with version control and android APIs
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private SubscriptionDao SubscriptionDao;
        private PopulateDbAsyncTask(SubscriptionDatabase db) {
            SubscriptionDao = db.subscriptionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            SubscriptionDao.insert(new Subscription("Netflix", "Family", 899, 4, 30, R.drawable.netflix));
            SubscriptionDao.insert(new Subscription("Spotify", "Duo", 129, 2, 30, R.drawable.spotify));
            SubscriptionDao.insert(new Subscription("PrimeVideo", "Family", 999, 4, 30, R.drawable.primevideo));
            SubscriptionDao.insert(new Subscription("Netflix", "Individual", 129, 1, 30, R.drawable.netflix));
            SubscriptionDao.insert(new Subscription("Spotify", "Family", 199, 4, 30, R.drawable.spotify));

            return null;
        }
    }

}
