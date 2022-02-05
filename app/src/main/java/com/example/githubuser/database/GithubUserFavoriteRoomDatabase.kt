package com.example.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuser.models.GithubUserFavoriteModel

@Database(entities = [GithubUserFavoriteModel::class], version = 1)
abstract class GithubUserFavoriteRoomDatabase : RoomDatabase(){
    abstract fun githubUserFavoriteDao(): GithubUserFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: GithubUserFavoriteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GithubUserFavoriteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(GithubUserFavoriteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        GithubUserFavoriteRoomDatabase::class.java, "github_favorite_user")
                        .build()
                }
            }
            return INSTANCE as GithubUserFavoriteRoomDatabase
        }
    }
}