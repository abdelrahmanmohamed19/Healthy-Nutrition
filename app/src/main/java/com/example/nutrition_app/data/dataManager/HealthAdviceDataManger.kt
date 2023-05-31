package com.example.nutrition_app.data.dataManager

import android.os.Parcel
import android.os.Parcelable
import com.example.nutrition_app.data.model.HealthAdvice

class HealthAdviceDataManger() : Parcelable {
    private val healthAdvices: MutableList<HealthAdvice> = mutableListOf()

    constructor(parcel: Parcel) : this()

    fun addHealthAdvice(healthAdvice: HealthAdvice) {
        healthAdvices.add(healthAdvice)
    }

    fun getHealthAdvices(): MutableList<HealthAdvice> = healthAdvices

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HealthAdviceDataManger> {
        override fun createFromParcel(parcel: Parcel): HealthAdviceDataManger {
            return HealthAdviceDataManger(parcel)
        }

        override fun newArray(size: Int): Array<HealthAdviceDataManger?> {
            return arrayOfNulls(size)
        }
    }
}