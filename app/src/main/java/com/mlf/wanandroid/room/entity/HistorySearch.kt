package com.mlf.wanandroid.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "history_search")
data class HistorySearch(
	@PrimaryKey(autoGenerate = true)
	val id: Int,
	@ColumnInfo(name = "search_content")
	val content: String
)