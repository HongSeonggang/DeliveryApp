package eu.tutorials.delapp

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

    @Database(entities = [Entity_DeliveryDB::class], version = 1)
    abstract class DB_Delivery: RoomDatabase() {
        abstract fun deliveryDao(): Entity_DeliveryDB_DAO


    }
