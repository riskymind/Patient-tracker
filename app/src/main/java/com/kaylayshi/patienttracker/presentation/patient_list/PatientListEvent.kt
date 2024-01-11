package com.kaylayshi.patienttracker.presentation.patient_list

import com.kaylayshi.patienttracker.domain.model.Patient

sealed class PatientListEvent {
    data class DeletePatient(val patient: Patient) : PatientListEvent()
    object GetPatients : PatientListEvent()
    object NavigateToDetails : PatientListEvent()
}
