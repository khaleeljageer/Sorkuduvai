package com.jskaleel.sorkuduvai.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jskaleel.sorkuduvai.db.entities.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {
    @Query("select * from recent_search order by time_stamp desc")
    fun getRecentSearch(): Flow<MutableList<RecentSearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RecentSearchEntity)

    @Query("delete from recent_search where time_stamp =:timeStamp")
    suspend fun deleteSearch(timeStamp: Long)
}