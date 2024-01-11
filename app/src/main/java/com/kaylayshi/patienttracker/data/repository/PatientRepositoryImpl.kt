package com.kaylayshi.patienttracker.data.repository

import com.kaylayshi.patienttracker.data.local.PatientDao
import com.kaylayshi.patienttracker.domain.model.Patient
import com.kaylayshi.patienttracker.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val dao: PatientDao
) : PatientRepository {

    override suspend fun addOrUpdatePatient(patient: Patient) {
        dao.addOrUpdatePatient(patient)
    }

    override suspend fun deletePatient(patient: Patient) {
        dao.deletePatient(patient)
    }

    override suspend fun getPatientByID(id: Int): Patient? = dao.getPatientById(id)


    override fun getAllPatients(): Flow<List<Patient>> = dao.getAllPatient()

}