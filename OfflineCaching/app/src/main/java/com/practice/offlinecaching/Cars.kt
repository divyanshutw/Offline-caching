package com.practice.offlinecaching

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="cars_table")
data class Cars(
    @PrimaryKey(autoGenerate = true)
    var cardId:Long=0,

    @ColumnInfo(name="image_url")
    var imageURL:String?=null,

    @ColumnInfo(name="name")
    var name:String?=null,

    @ColumnInfo(name="category")
    var category:String?=null

)