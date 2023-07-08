package com.example.unaikcraft.application

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.unaikcraft.Dao.craftDao
import com.example.unaikcraft.model.CraftModel


@Database(entities = [CraftModel::class], version = 1, exportSchema = false)
abstract class UnaikDatabase : RoomDatabase() {
    abstract fun crafDao(): craftDao

    companion object {
        private var INSTANCE: UnaikDatabase? = null

        fun getDatabase(context: Context): UnaikDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UnaikDatabase::class.java,
                    "unaik_database"
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}