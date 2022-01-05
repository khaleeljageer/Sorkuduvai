package com.jskaleel.sorkuduvai.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_search")
data class RecentSearchEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "time_stamp")
    val timeStamp: Long,

    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "detail_json")
    val details: String
)