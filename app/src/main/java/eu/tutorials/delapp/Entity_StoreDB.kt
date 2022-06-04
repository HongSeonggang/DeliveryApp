package eu.tutorials.delapp

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Entity_StoreDB (

    var sotrename : String,
    var info: String,
    var location : Int
){
    //가게 시리얼 넘버(고유번호)
    @PrimaryKey(autoGenerate = true) var ssn: Int = 0
}