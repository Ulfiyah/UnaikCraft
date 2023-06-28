package com.example.unaikcraft.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.unaikcraft.model.CraftModel
import kotlinx.coroutines.flow.Flow

@Dao
interface craftDao {
    @Query("SELECT* from craft_table order by nama ASC")
    fun getAllcraft():Flow<List<CraftModel>>
    @Insert
    suspend fun insertCraft (craftModel:CraftModel)
    @Delete
    suspend fun deleteCraft (craftModel: CraftModel)
    @Update
    fun updateCraft (craftModel: CraftModel)
}

