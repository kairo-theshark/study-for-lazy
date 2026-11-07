package com.artem.studyforlazy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artem.studyforlazy.data.local.entities.DeadlineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeadlineDao {

    @Query("SELECT * FROM deadlines ORDER BY isCompleted ASC, dueDate ASC")
    fun observeAll(): Flow<List<DeadlineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(deadlines: List<DeadlineEntity>)

    @Query("UPDATE deadlines SET isCompleted = NOT isCompleted WHERE id = :id")
    suspend fun toggleCompleted(id: String)

    @Query("DELETE FROM deadlines WHERE id = :id")
    suspend fun delete(id: String)
}
