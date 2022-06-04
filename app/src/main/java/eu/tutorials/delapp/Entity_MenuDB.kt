package eu.tutorials.delapp

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Entity_MenuDB (
    var menuname : String,
    var menuprice : Int

){
    //가게 시리얼 넘버를 같이 쓰면 메뉴 고유번호 필요없는지?
    @PrimaryKey(autoGenerate = true) var menusn: Int = 0

}