package com.example.nutrition_app.util.extention

fun String.toPureNumber(): Double {
    return when {
        this.contains(" mcg") -> {
            this.dropLast(4).toDouble()
        }
        this.contains(" mg") -> {
            this.dropLast(3).toDouble()
        }
        this.contains(" IU") -> {
            this.dropLast(3).toDouble()
        }
        this.contains(" g") -> {
            this.dropLast(2).toDouble()
        }

        this.contains("g") -> {
            this.dropLast(1).toDouble()
        }
        else -> 0.0
    }
}

fun String.removeQuotations(): String {
    return if (this.contains("\"")) {
        this.replace("\"", "")
    } else {
        this
    }
}