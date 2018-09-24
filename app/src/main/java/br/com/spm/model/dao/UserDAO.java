package br.com.spm.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import br.com.spm.model.entity.UserEntity;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserEntity user);

    @Query("SELECT * FROM user LIMIT 1")
    LiveData<UserEntity> getUserEntity();

//    @Query("SELECT * FROM user WHERE register = :register AND password = :password")
//    LiveData<UserEntity> getUserEntity(String register, String password);
}
