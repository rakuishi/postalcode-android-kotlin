package com.rakuishi.postalcode2

class Constant {

    companion object {
        const val ZIPPED_DATABASE_NAME = "x-ken-all-201704.db.zip"
        const val DATABASE_NAME = "x-ken-all-201704.db"

        enum class ViewType {
            PREFECTURE,
            CITY,
            STREET,
            DETAIL
        }
    }
}