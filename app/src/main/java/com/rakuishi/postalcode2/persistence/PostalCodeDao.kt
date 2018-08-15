package com.rakuishi.postalcode2.persistence

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

@Dao
interface PostalCodeDao {

    @Query("SELECT * FROM postalcode GROUP BY prefecture_id, prefecture, prefecture_yomi")
    fun findPrefectures(): List<PostalCode>
}