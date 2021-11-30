package com.erkprog.rateit.model

enum class Rating(val title: String, val value: Int) {
    HIDEOUS("Hideous", 1),
    OK("Ok", 2),
    GOOD("Good", 3)
}