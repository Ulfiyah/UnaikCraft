package com.example.unaikcraft.Repository
import com.example.unaikcraft.Dao.craftDao
import com.example.unaikcraft.model.CraftModel
import kotlinx.coroutines.flow.Flow

class CraftRepository (private val craftDao: craftDao){
    val allcraft : Flow<List<CraftModel>> = craftDao.getAllcraft()

    suspend fun insertCraft (craftModel: CraftModel){
        craftDao.insertCraft(craftModel)
    }
    suspend fun deleteCraft (craftModel: CraftModel){
        craftDao.deleteCraft(craftModel)
    }
    suspend fun updateCraft (craftModel: CraftModel){
        craftDao.updateCraft(craftModel)
    }




}