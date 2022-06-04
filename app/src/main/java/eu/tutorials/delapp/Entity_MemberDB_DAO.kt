package eu.tutorials.delapp

import androidx.room.*


    @Dao
    interface Entity_MemberDB_DAO {
        @Query ("SELECT * FROM Entity_MemberDB")
        fun getAll() : List<Entity_MemberDB>
        @Insert
        fun insert(user: Entity_MemberDB)

        @Update
        fun update(user: Entity_MemberDB)

        @Delete
        fun delete(user: Entity_MemberDB)
    }
