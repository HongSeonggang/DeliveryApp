package eu.tutorials.delapp

import androidx.room.Entity
import androidx.room.PrimaryKey


    @Entity
    data class Entity_MemberDB (
        var id : String,
        var pw: String,
        var phoneNum : String,
        var deliveryOption : Boolean,
        var cash : Int


    ){
        //멤버 시리얼 넘버(고유번호)
        @PrimaryKey(autoGenerate = true) var msn: Int = 0
    }
