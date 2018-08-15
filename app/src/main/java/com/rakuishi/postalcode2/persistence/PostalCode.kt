package com.rakuishi.postalcode2.persistence

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "postalcode")
data class PostalCode(@PrimaryKey @ColumnInfo(name = "code") val code: String,
                      @ColumnInfo(name = "prefecture_id") val prefectureId: Int,
                      @ColumnInfo(name = "city_id") val cityId: Int,
                      @ColumnInfo(name = "prefecture") val prefecture: String,
                      @ColumnInfo(name = "city") val city: String,
                      @ColumnInfo(name = "street") val street: String,
                      @ColumnInfo(name = "prefecture_yomi") val prefectureYomi: String,
                      @ColumnInfo(name = "city_yomi") val cityYomi: String,
                      @ColumnInfo(name = "street_yomi") val streetYomi: String)