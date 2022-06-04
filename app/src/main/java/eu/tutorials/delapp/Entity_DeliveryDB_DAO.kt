package eu.tutorials.delapp

import androidx.room.*


@Dao
interface Entity_DeliveryDB_DAO {
    @Query ("SELECT * FROM Entity_DeliveryDB")
    fun getAll() : List<Entity_DeliveryDB>

    @Insert
    fun insert(del: Entity_DeliveryDB)

    @Update
    fun update(del: Entity_DeliveryDB)

    @Delete
    fun delete(del: Entity_DeliveryDB)
}