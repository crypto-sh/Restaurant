package com.restaurant.core.dto

enum class SortType(val value : String) {

    Undefine(""),
    OpenStatus("1"),
    Popularity("2"),
    Distance("3");

    companion object {

        operator fun get(value: String) : SortType {
            for (item in values()) {
                if (item.value == value) {
                    return item
                }
            }
            return Undefine
        }
    }

}