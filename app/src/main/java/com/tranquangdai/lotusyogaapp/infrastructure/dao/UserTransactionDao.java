package com.tranquangdai.lotusyogaapp.infrastructure.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.UserTransaction;

import java.util.List;

@Dao
public interface UserTransactionDao {
    @Query("SELECT * FROM UserTransaction")
    List<UserTransaction> getAllTransactions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTransaction(UserTransaction userTransaction);
}
