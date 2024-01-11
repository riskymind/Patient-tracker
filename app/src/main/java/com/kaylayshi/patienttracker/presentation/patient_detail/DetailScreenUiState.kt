package com.kaylayshi.patienttracker.presentation.patient_detail

data class DetailScreenUiState(
    val name: String = "",
    val age: String = "",
    val assignee: String = "",
    val prescription: String = "",
    val gender: Int = 0
)
