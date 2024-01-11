package com.kaylayshi.patienttracker.domain.repository

import com.kaylayshi.patienttracker.domain.model.Patient
import kotlinx.coroutines.flow.Flow

interface PatientRepository {

    suspend fun addOrUpdatePatient(patient: Patient)

    suspend fun deletePatient(patient: Patient)

    suspend fun getPatientByID(id: Int): Patient?

    fun getAllPatients(): Flow<List<Patient>>
}