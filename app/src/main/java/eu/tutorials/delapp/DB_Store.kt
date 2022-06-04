package eu.tutorials.delapp

import androidx.room.Database
import androidx.room.RoomDatabase

    @Database(entities = [Entity_StoreDB::class], version = 1)
    abstract class DB_Store: RoomDatabase() {
        abstract fun storeDao(): Entity_StoreDB_DAO
    }
