package com.kanade.ushio.arch

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.kanade.ushio.arch.room.*
import com.kanade.ushio.entity.*

@Database(entities = [
    UserToken::class,
    UserCollection::class,
    Subject::class,
    Ep::class,
    Actor::class,
    Crt::class,
    CalendarSubject::class,
    Calendar::class
],
        version = 1,
        exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userTokenDao(): UserTokenDao
    abstract fun userCollectionDao(): UserCollectionDao
    abstract fun subjectDao(): SubjectDao
    abstract fun epDao(): EpDao
    abstract fun actorDao(): ActorDao
    abstract fun crtDao(): CrtDao
    abstract fun calendarSubjectDao(): CalendarSubjectDao
    abstract fun calendarDao(): CalendarDao

    companion object {

        @Volatile
        private lateinit var INSTANCE: AppDatabase

        @JvmStatic
        fun init(context: Context) {
            buildDatabase(context).also { INSTANCE = it }
        }

        fun getInstance(): AppDatabase = INSTANCE

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "ushio.db")
                        .build()
    }
}