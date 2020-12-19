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



@Database(entities = {Subscription.class, SubscriptionGroup.class}, version = 3)
public abstract class SubscriptionDatabase extends RoomDatabase {

    private static SubscriptionDatabase instance;

    public abstract SubscriptionDao subscriptionDao();

    public static synchronized SubscriptionDatabase getInstance(Context context) {
        //we only create an instance of our database if it doesn't already exist
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SubscriptionDatabase.class, "subscription_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() //this has to do with version control and android APIs
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        System.out.println("Instance destroyed!");
        instance = null;
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
             SubscriptionDao= db.subscriptionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            SubscriptionDao.insert(new Subscription("NetFam30", "Netflix", "Family", 899, 4, 30, R.drawable.netflix, "14/12/2020"));
            SubscriptionDao.insert(new Subscription("SpotDuo90", "Spotify", "Duo", 399, 2, 90, R.drawable.spotify, "06/11/2020"));
            SubscriptionDao.insert(new Subscription("PrimeFam365", "PrimeVideo", "Family", 999, 4, 365, R.drawable.primevideo, "25/06/2020"));
            SubscriptionDao.insert(new Subscription("NetInd30", "Netflix", "Individual", 129, 1, 30, R.drawable.netflix, "17/12/2020"));
            SubscriptionDao.insert(new Subscription("SpotFam30", "Spotify", "Family", 199, 4, 30, R.drawable.spotify, "7/12/2020"));

            SubscriptionDao.insertUser(new SubscriptionGroup("Michael Scott", "NetFam30"));
            SubscriptionDao.insertUser(new SubscriptionGroup("Jim Halpert", "NetFam30"));
            SubscriptionDao.insertUser(new SubscriptionGroup("Pam Beesly", "NetFam30"));

            SubscriptionDao.insertUser(new SubscriptionGroup("Anirudh", "SpotDuo90"));
            SubscriptionDao.insertUser(new SubscriptionGroup("Irene", "SpotDuo90"));

            SubscriptionDao.insertUser(new SubscriptionGroup("Rachel", "PrimeFam365"));
            SubscriptionDao.insertUser(new SubscriptionGroup("Ross", "PrimeFam365"));
            SubscriptionDao.insertUser(new SubscriptionGroup("Monica", "PrimeFam365"));
            SubscriptionDao.insertUser(new SubscriptionGroup("Phoebe", "PrimeFam365"));

            SubscriptionDao.insertUser(new SubscriptionGroup("Toby", "NetInd30"));

            SubscriptionDao.insertUser(new SubscriptionGroup("Homer", "SpotFam30"));
            SubscriptionDao.insertUser(new SubscriptionGroup("Marge", "SpotFam30"));
            SubscriptionDao.insertUser(new SubscriptionGroup("Lisa", "SpotFam30"));
            SubscriptionDao.insertUser(new SubscriptionGroup("Bart", "SpotFam30"));
            return null;
        }
    }

}
