package eu.tutorials.delapp

import androidx.room.Database
import androidx.room.RoomDatabase

class DB_Member {
    @Database(entities = [Entity_MemberDB::class], version = 1)
    abstract class UserDatabase: RoomDatabase() {
        abstract fun userDao(): Entity_MemberDB_DAO
    }
}