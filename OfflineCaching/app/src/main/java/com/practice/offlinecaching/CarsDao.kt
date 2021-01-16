package com.practice.offlinecaching

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CarsDao
{
    @Query("select * from cars_table where category= :key")
    fun getCarsFromRoom(key:String): LiveData<List<Cars>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCarsToRoom(cars:List<Cars>)
}

@Database(entities = [Cars::class],version=1,exportSchema = false)
abstract class CarsDatabase : RoomDatabase() {
    abstract val CarsDatabaseDao: CarsDao
}

private lateinit var INSTANCE:CarsDatabase

fun getDatabase(context: Context):CarsDatabase
{
    if(!::INSTANCE.isInitialized)
    {
        INSTANCE=
            Room.databaseBuilder(context.applicationContext,CarsDatabase::class.java,"cars_database")
                .fallbackToDestructiveMigration().build()

    }
    return INSTANCE
}
