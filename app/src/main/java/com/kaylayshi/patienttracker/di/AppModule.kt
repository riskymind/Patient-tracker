package com.kaylayshi.patienttracker.di

import android.content.Context
import androidx.room.Room
import com.kaylayshi.patienttracker.data.local.PatientDB
import com.kaylayshi.patienttracker.data.repository.PatientRepositoryImpl
import com.kaylayshi.patienttracker.domain.repository.PatientRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDB(
        @ApplicationContext context: Context
    ): PatientDB {
        return Room.databaseBuilder(context, PatientDB::class.java, "patient_db").build()
    }


    @Provides
    @Singleton
    fun providePatientRepo(
        db: PatientDB
    ): PatientRepository {
        return PatientRepositoryImpl(db.patientDao)
    }

}