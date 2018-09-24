package br.com.spm.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.spm.model.dao.SiteDAO;
import br.com.spm.model.entity.SiteEntity;

@Database(entities = {SiteEntity.class}, version = 1)
public abstract class SPMDataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "spm";
    private static SPMDataBase instance;

    public abstract SiteDAO siteDAO();

    public static SPMDataBase getInstance(final Context context) {
        if (instance == null) {
            synchronized (SPMDataBase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), SPMDataBase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }

}
