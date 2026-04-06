package com.rodrip.marketya.productList.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rodrip.marketya.productList.data.local.database.entity.PromotionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PromotionDao {

    @Query("SELECT * FROM promotions")
    fun getAllPromotions(): Flow<List<PromotionEntity>>

    @Query("SELECT * FROM promotions WHERE id = :id")
    fun getPromotionById(id: String): Flow<PromotionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPromotions(promotions: List<PromotionEntity>)

    @Query("DELETE FROM promotions")
    suspend fun deleteAllPromotions()

    @Transaction
    suspend fun refreshPromotions(promotions: List<PromotionEntity>) {
        deleteAllPromotions()
        insertPromotions(promotions)
    }


}