package br.com.spm.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.spm.model.entity.SiteEntity;

@Dao
public interface SiteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SiteEntity siteEntity);

    @Query("SELECT * FROM site")
    LiveData<List<SiteEntity>> getSiteEntityAll();

    @Query("SELECT * FROM site WHERE id = :id")
    LiveData<SiteEntity> getSiteEntity(int id);

    @Delete
    void deleteSite(SiteEntity siteEntity);

    @Update
    void updateSite(SiteEntity siteEntity);

}
