package eu.tutorials.delapp

import androidx.room.*


@Dao
interface Entity_MenuDB_DAO {
    @Query ("SELECT * FROM Entity_MenuDB")
    fun getAll() : List<Entity_MenuDB>
    @Insert
    fun insert(user: Entity_MenuDB)

    @Update
    fun update(user: Entity_MenuDB)

    @Delete
    fun delete(user: Entity_MenuDB)
}