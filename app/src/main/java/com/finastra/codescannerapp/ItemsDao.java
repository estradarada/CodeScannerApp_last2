package com.finastra.codescannerapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao
public interface ItemsDao {

    @Insert
    void insert(EntityItems entityItems);

    @Query("SELECT * FROM entityitems")
    List<EntityItems> getAllEntityItems();

    @Query("SELECT * FROM entityitems WHERE entityId=:entityId")
    List<EntityItems> getEntityItem(String entityId);

   @Query("SELECT * FROM entityitems ORDER BY entityId DESC LIMIT 1")
   List<EntityItems> getLastEntityItem();

    @Query("DELETE FROM entityitems")
    void deleteAll();




}
