package com.codesroots.osamaomar.grz.datalayer.localdata;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.codesroots.osamaomar.grz.datalayer.localdata.product.deo.ProductDao;
import com.codesroots.osamaomar.grz.datalayer.localdata.product.entities.ProductDB;

@Database(entities = {ProductDB.class},version = 1,exportSchema = false)

public abstract class LocalDatabase extends RoomDatabase{


    public abstract ProductDao productDeo ();


    public static LocalDatabase instance;

    public static LocalDatabase getInstance (Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),LocalDatabase.class,
                    "cart_database")
            .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
