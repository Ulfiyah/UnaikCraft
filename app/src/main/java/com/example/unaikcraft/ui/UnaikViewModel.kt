package com.example.unaikcraft.ui

import android.provider.ContactsContract.Intents.Insert
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.unaikcraft.Repository.CraftRepository
import com.example.unaikcraft.model.CraftModel
import kotlinx.coroutines.launch

class UnaikViewModel (private val repository: CraftRepository): ViewModel() {
    val allcraft: LiveData<List<CraftModel>> = repository.allcraft.asLiveData()

    fun insert(craftModel: CraftModel) = viewModelScope.launch {
        repository.insertCraft(craftModel)
    }
    fun delete(craftModel: CraftModel) = viewModelScope.launch {
        repository.deleteCraft(craftModel)
    }
    fun update(craftModel: CraftModel) = viewModelScope.launch {
        repository.updateCraft(craftModel)
    }
}

class UnaikViewModelFactory (private val repository: CraftRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((UnaikViewModel::class.java))) {
            return UnaikViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}