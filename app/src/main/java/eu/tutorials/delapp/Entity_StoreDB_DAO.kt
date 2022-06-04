package eu.tutorials.delapp

import androidx.room.*


@Dao
interface Entity_StoreDB_DAO {
    @Query ("SELECT * FROM Entity_StoreDB")
    fun getAll() : List<Entity_StoreDB>
    @Insert
    fun insert(user: Entity_StoreDB)

    @Update
    fun update(user: Entity_StoreDB)

    @Delete
    fun delete(user: Entity_StoreDB)
}