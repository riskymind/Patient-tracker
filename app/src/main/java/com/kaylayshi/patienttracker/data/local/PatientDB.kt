package com.kaylayshi.patienttracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kaylayshi.patienttracker.domain.model.Patient

@Database(entities = [Patient::class], version = 1)
abstract class PatientDB : RoomDatabase() {

    abstract val patientDao: PatientDao
}