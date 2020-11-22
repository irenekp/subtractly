package com.example.mainpage_subapp.roomdata;

//DAOs are Data-access-objects that are used to insert, query, update data etc. These guys are used to interact with the database.
//DAOs are never classes - they are either interfaces or abstract classes - since we just need to tell room 'hey, run your methods here, and
//the actual methods are provided by room itself for querying things.

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubscriptionDao {

    //insert, update and delete functions are handled completely by room
    //so we just create empty functions and pass arguments, and annotate these with the appropriate tags like so:

    @Insert
    void insert(Subscription subscription);

    @Update
    void Update(Subscription subscription);

    @Delete
    void Delete(Subscription subscription);

    //for any and all customised operations - you use a @Query tag and add your query, like so:
    @Query("SELECT * FROM subscriptions")
    List<Subscription> getAllSubscriptions();

}
