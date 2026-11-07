package com.artem.studyforlazy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artem.studyforlazy.data.local.entities.LessonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM lessons WHERE dayOfWeek = :dayOfWeek AND isOddWeek = :isOddWeek ORDER BY startTime")
    fun observeLessons(dayOfWeek: Int, isOddWeek: Boolean): Flow<List<LessonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(lessons: List<LessonEntity>)
}
