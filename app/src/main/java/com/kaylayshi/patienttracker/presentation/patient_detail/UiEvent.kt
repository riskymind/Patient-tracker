package com.kaylayshi.patienttracker.presentation.patient_detail

sealed class UiEvent {
    data class ShowToast(val msg: String) : UiEvent()
    object SaveNote : UiEvent()
}
