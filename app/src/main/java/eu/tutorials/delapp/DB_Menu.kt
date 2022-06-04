package eu.tutorials.delapp

import androidx.room.Database
import androidx.room.RoomDatabase

    @Database(entities = [Entity_MenuDB::class], version = 1)
    abstract class DB_Menu: RoomDatabase() {
        abstract fun menuDao(): Entity_MenuDB_DAO
    }

