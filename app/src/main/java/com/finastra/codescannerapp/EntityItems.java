package com.finastra.codescannerapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EntityItems {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int entityId;

    @NonNull
    private String objectName;

    @NonNull
    private String item_id;



    public EntityItems() {
    }



    public EntityItems(int entityId, @NonNull String objectName, @NonNull String item_id) {
        this.entityId = entityId;
        this.objectName = objectName;
        this.item_id = item_id;

    }


    public int getEntityId() {
        return entityId;
    }

    @NonNull
    public String getObjectName() {
        return objectName;
    }

    @NonNull
    public String getItem_id() {
        return item_id;
    }



    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public void setObjectName(@NonNull String objectName) {
        this.objectName = objectName;
    }

    public void setItem_id(@NonNull String item_id) {
        this.item_id = item_id;
    }


}

