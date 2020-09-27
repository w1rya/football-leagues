package com.wiryatech.footballleagues.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DbOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "FavoriteEvent.db", null, 1) {

    companion object {
        private var instance: DbOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): DbOpenHelper {
            if (instance == null) {
                instance = DbOpenHelper(context)
            }
            return instance as DbOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            Favorite.TABLE_FAVORITE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.EVENT_ID to TEXT + UNIQUE,
            Favorite.EVENT_NAME to TEXT,
            Favorite.EVENT_DATE to TEXT,
            Favorite.LEAGUE_NAME to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Favorite.TABLE_FAVORITE, true)
    }

}

val Context.db: DbOpenHelper
    get() = DbOpenHelper.getInstance(applicationContext)