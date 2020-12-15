package com.example.mainpage_subapp.roomdata;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mainpage_subapp.R;
import com.example.mainpage_subapp.ui.home.SubscriptionDetails;

import java.net.URL;
import java.util.Date;



@Database(entities = {Subscription.class, SubscriptionGroup.class}, version = 2)
public abstract class SubscriptionDatabase extends RoomDatabase {

    private static SubscriptionDatabase instance;

    public abstract SubscriptionDao subscriptionDao();

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
            SubscriptionDao.insert(new Subscription("Spotify", "Duo", 399, 2, 90, R.drawable.spotify));
            SubscriptionDao.insert(new Subscription("PrimeVideo", "Family", 999, 4, 365, R.drawable.primevideo));
            SubscriptionDao.insert(new Subscription("Netflix", "Individual", 129, 1, 30, R.drawable.netflix));
            SubscriptionDao.insert(new Subscription("Spotify", "Family", 199, 4, 30, R.drawable.spotify));

            return null;
        }
    }

}
