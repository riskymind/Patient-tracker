package com.kaylayshi.patienttracker.presentation.patient_detail

sealed class DetailEvents {
    data class EnterName(val name: String) : DetailEvents()
    data class EnterAge(val age: String) : DetailEvents()
    data class EnterAssignee(val doctor: String) : DetailEvents()
    data class EnterPrescription(val prescription: String) : DetailEvents()
    object SelectMale : DetailEvents()
    object SelectFemale : DetailEvents()
    object Save : DetailEvents()
}
