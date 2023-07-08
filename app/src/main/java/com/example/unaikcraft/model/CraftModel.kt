package com.example.unaikcraft.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "craft_table")
data class CraftModel(
    @PrimaryKey(autoGenerate = true )
    val id: Int = 0,
    val nama: String,
    val alamat: String,
    val nohp: String,

    ) : Parcelable