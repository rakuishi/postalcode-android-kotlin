package com.rakuishi.postalcode2.persistence

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

@Dao
interface PostalCodeDao {

    @Query("SELECT * FROM postalcode GROUP BY prefecture_id, prefecture, prefecture_yomi")
    fun findPrefectures(): List<PostalCode>

    @Query("SELECT * FROM postalcode WHERE prefecture_id = :prefectureId GROUP BY city_id, city, city_yomi")
    fun findCities(prefectureId: Int): List<PostalCode>

    @Query("SELECT * FROM postalcode WHERE city_id = :cityId AND street != ''")
    fun findStreets(cityId: Int): List<PostalCode>
}