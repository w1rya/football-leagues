package com.wiryatech.footballleagues.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DbOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Favorite.db", null, 1) {

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
            FavoriteMatch.TABLE_FAVORITE_MATCH, true,
            FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.EVENT_ID to TEXT + UNIQUE,
            FavoriteMatch.EVENT_NAME to TEXT,
            FavoriteMatch.EVENT_DATE to TEXT,
            FavoriteMatch.LEAGUE_NAME to TEXT
        )
        db?.createTable(
            FavoriteTeams.TABLE_FAVORITE_TEAM, true,
            FavoriteTeams.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeams.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeams.TEAM_NAME to TEXT,
            FavoriteTeams.TEAM_BADGE to TEXT,
            FavoriteTeams.TEAM_DESC to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteMatch.TABLE_FAVORITE_MATCH, true)
        db?.dropTable(FavoriteTeams.TABLE_FAVORITE_TEAM, true)
    }

}

val Context.db: DbOpenHelper
    get() = DbOpenHelper.getInstance(applicationContext)