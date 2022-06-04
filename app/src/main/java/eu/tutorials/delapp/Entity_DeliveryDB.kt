package eu.tutorials.delapp

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Entity_DeliveryDB (

    var distance : String,
    //var destination: String,
    //var distance : Int,
    //var delprice : Int
){
    @PrimaryKey(autoGenerate = true) var dsn: Int = 0//배달 시리얼 넘버(고유번호)

}