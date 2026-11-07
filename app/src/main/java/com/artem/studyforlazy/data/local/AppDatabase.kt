package com.artem.studyforlazy.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.artem.studyforlazy.data.local.dao.AttendanceDao
import com.artem.studyforlazy.data.local.dao.CampusDao
import com.artem.studyforlazy.data.local.dao.DeadlineDao
import com.artem.studyforlazy.data.local.dao.ScheduleDao
import com.artem.studyforlazy.data.local.entities.AttendanceEntity
import com.artem.studyforlazy.data.local.entities.CampusEntity
import com.artem.studyforlazy.data.local.entities.DeadlineEntity
import com.artem.studyforlazy.data.local.entities.LessonEntity

@Database(
    entities = [CampusEntity::class, LessonEntity::class, DeadlineEntity::class, AttendanceEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun scheduleDao(): ScheduleDao
    abstract fun deadlineDao(): DeadlineDao
    abstract fun campusDao(): CampusDao
    abstract fun attendanceDao(): AttendanceDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "study_for_lazy.db",
            ).build().also { instance = it }
        }
    }
}
