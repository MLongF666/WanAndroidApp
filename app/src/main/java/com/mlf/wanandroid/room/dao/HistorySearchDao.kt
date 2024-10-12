package com.mlf.wanandroid.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mlf.wanandroid.room.entity.HistorySearch

@Dao
interface HistorySearchDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertHistorySearch(historySearch: HistorySearch)
	@Query("SELECT * FROM history_search")
	suspend fun getAllHistorySearch():List<HistorySearch>
	@Query("DELETE FROM history_search")
	suspend fun deleteAllHistorySearch()
	@Query("SELECT * FROM history_search where search_content = :content")
	suspend fun searchHistory(content:String):List<HistorySearch>
	@Query("DELETE FROM history_search WHERE id = :id")
	suspend fun deleteHistorySearchById(id: Int)


}