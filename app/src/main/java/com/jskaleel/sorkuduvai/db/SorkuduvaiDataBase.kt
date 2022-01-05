package com.jskaleel.sorkuduvai.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jskaleel.sorkuduvai.db.dao.RecentSearchDao
import com.jskaleel.sorkuduvai.db.entities.RecentSearchEntity

@Database(entities = [RecentSearchEntity::class], version = 1, exportSchema = false)
abstract class SorkuduvaiDataBase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao
}