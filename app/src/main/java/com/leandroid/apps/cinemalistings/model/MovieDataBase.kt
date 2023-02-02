package com.leandroid.apps.cinemalistings.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leandroid.apps.cinemalistings.ui.utils.Converters


@Database(entities = [Movie::class], version = 1)
@TypeConverters(Converters::class)
abstract class MovieDataBase : RoomDatabase(){

    abstract fun movieDao(): MovieDao

    companion object{
        private var INSTANCE : MovieDataBase? = null

        fun getDataBase(context: Context):MovieDataBase{

            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MovieDataBase::class.java,
                    "moviesDB"
                ).build()
            }
            return INSTANCE!!
        }
    }
}