package com.artem.studyforlazy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artem.studyforlazy.data.local.entities.CampusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CampusDao {

    @Query("SELECT * FROM campuses")
    fun observeAll(): Flow<List<CampusEntity>>

    @Query("SELECT COUNT(*) FROM campuses")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(campuses: List<CampusEntity>)
}
