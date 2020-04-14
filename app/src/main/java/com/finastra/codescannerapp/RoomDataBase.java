package com.finastra.codescannerapp;

import android.animation.TypeConverter;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = EntityItems.class, version = 2)

public abstract class RoomDataBase extends RoomDatabase {

    public  abstract ItemsDao itemsDao();


}