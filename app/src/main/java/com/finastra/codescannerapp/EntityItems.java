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
    private String objectName, worker08;

    @NonNull
    private String item_id;

    @NonNull
    private long date;



    public EntityItems() {
    }

    public EntityItems(int entityId, @NonNull String objectName, @NonNull String worker08,
                       @NonNull String item_id, long date) {
        this.entityId = entityId;
        this.objectName = objectName;
        this.worker08 = worker08;
        this.item_id = item_id;
        this.date = date;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    @NonNull
    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(@NonNull String objectName) {
        this.objectName = objectName;
    }

    @NonNull
    public String getWorker08() {
        return worker08;
    }

    public void setWorker08(@NonNull String worker08) {
        this.worker08 = worker08;
    }

    @NonNull
    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(@NonNull String item_id) {
        this.item_id = item_id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}

