package com.example.unaikcraft.application

import android.app.Application
import com.example.unaikcraft.Repository.CraftRepository

class UnaikApp : Application() {
    val database by lazy { UnaikDatabase.getDatabase(this) }
    val repository by lazy { CraftRepository(database.crafDao()) }
}