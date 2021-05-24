package com.example.handcalulatortest4.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class MySqlHelper(context: Context) : SQLiteOpenHelper(context, "MyDatabase1", null, 2) {

    companion object {
        // Results table attributes
        val TABLE_NAME_PLAY_RESULTS:String  = "RESULTS"
        val ID_COLUMN:String  = "_id"
        val NAME_COLUMN:String  = "NAME"
        val LAST_RESULT_COLUMN:String  = "LAST"
        val SUM_OF_RESULTS_COLUMN:String  = "SUM"
        val GAME_ID_COLUMN:String  = "GAME_ID"
        val TABLE_NAME_GAMEES:String  = "GAMES"
        val ID_COLUMN_GAME:String  = "_id"

    }

    private  val SQL_CREATE_ENTRIES_RESULTS =
        "CREATE TABLE $TABLE_NAME_PLAY_RESULTS ( $ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, $NAME_COLUMN TEXT NOT NULL," +
                " $LAST_RESULT_COLUMN INT NOT NULL, $SUM_OF_RESULTS_COLUMN INT NOT NULL, $GAME_ID_COLUMN INT NOT NULL)"


    private val SQL_CREATE_ENTRIES_GAME =
        "CREATE TABLE $TABLE_NAME_GAMEES ($ID_COLUMN_GAME INTEGER  PRIMARY KEY AUTOINCREMENT) ;";

    private val SQL_DELETE_ENTRIES_RESULTS = "DROP TABLE IF EXISTS $TABLE_NAME_PLAY_RESULTS"
    private val SQL_DELETE_ENTRIES_GAME = "DROP TABLE IF EXISTS $TABLE_NAME_GAMEES"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES_RESULTS)
        db?.execSQL(SQL_CREATE_ENTRIES_GAME)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES_GAME)
        db?.execSQL(SQL_DELETE_ENTRIES_RESULTS)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db,oldVersion,newVersion)
    }

    fun addHighestScore():Long {
        val mdb:SQLiteDatabase = this.writableDatabase
        val values = ContentValues().apply {
            put(ID_COLUMN_GAME,"")
        }
        return  mdb?.insert(TABLE_NAME_GAMEES,null,values)
    }

    fun getGameId():Int {
        val mdb:SQLiteDatabase = this.readableDatabase
        var cursor:Cursor? = null
        try{
            cursor = mdb.rawQuery("Select Max(VALUE) from $TABLE_NAME_GAMEES;",null)
            if(cursor.moveToFirst())
            {
                return cursor.getInt(0)
            }
        }catch (e:SQLiteException)
        {
            return  0
        }
        return  0
        // Select max(Value) from Highest ;

    }

    fun addResult(name:String,lastResult:Int,sumOfResults:Int,gameId:Int): Long? {
        val db = this.writableDatabase
        val v = ContentValues().apply {
            put(NAME_COLUMN,name)
            put(LAST_RESULT_COLUMN,lastResult)
            put(SUM_OF_RESULTS_COLUMN,sumOfResults)
            put(GAME_ID_COLUMN,gameId)
        }
        return  db?.insert(TABLE_NAME_PLAY_RESULTS,null,v)
    }

    fun getResult(): Cursor? {
        val db = this.readableDatabase
        return  db.query(TABLE_NAME_PLAY_RESULTS,null,null,null,null,null,"GAME_ID")
    }
}