package com.kaylayshi.patienttracker.presentation.patient_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaylayshi.patienttracker.domain.model.Patient
import com.kaylayshi.patienttracker.domain.repository.PatientRepository
import com.kaylayshi.patienttracker.utils.Constants.PATIENT_DETAILS_ARGUMENT_KEY
import com.kaylayshi.patienttracker.utils.TextFieldException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val patientRepository: PatientRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(DetailScreenUiState())


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    private var currentPatientId: Int? = null

    init {
        fetchPatientDetails()
    }

    fun onAction(event: DetailEvents) {
        when (event) {
            is DetailEvents.EnterAge -> {
                state = state.copy(age = event.age)
            }
            is DetailEvents.EnterAssignee -> {
                state = state.copy(assignee = event.doctor)
            }
            is DetailEvents.EnterName -> {
                state = state.copy(name = event.name)
            }
            is DetailEvents.EnterPrescription -> {
                state = state.copy(prescription = event.prescription)
            }
            DetailEvents.Save -> {
                viewModelScope.launch {
                    try {
                        savePatient()
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowToast(e.message ?: "Couldn't save patient's details.")
                        )
                    }
                }
            }
            DetailEvents.SelectFemale -> {
                state = state.copy(gender = 2)
            }
            DetailEvents.SelectMale -> {
                state = state.copy(gender = 1)
            }
        }
    }

    private fun savePatient() {
        val age = state.age.toIntOrNull()
        when {
            state.name.isEmpty() -> throw TextFieldException("Please Enter Name")
            state.age.isEmpty() -> throw TextFieldException("Please Enter Age")
            state.gender == 0 -> throw TextFieldException("Please Select Gender")
            state.assignee.isEmpty() -> throw TextFieldException("Please Enter Assignee")
            state.prescription.isEmpty() -> throw TextFieldException("Please Enter Prescription")
            age == null || age < 0 || age > 100 -> throw TextFieldException("PLease Enter A Valid Age")
        }

        val trimmedName = state.name.trim()
        val trimmedAssignee = state.assignee.trim()
        val trimmedPrescription = state.prescription.trim()
        viewModelScope.launch {
            patientRepository.addOrUpdatePatient(
                patient = Patient(
                    name = trimmedName,
                    age = state.age,
                    gender = state.gender,
                    doctorAssigned = trimmedAssignee,
                    prescription = trimmedPrescription,
                    id = currentPatientId
                )
            )
        }
    }

    private fun fetchPatientDetails() {
        savedStateHandle.get<Int>(key = PATIENT_DETAILS_ARGUMENT_KEY)?.let { patientId ->
            if (patientId != -1) {
                viewModelScope.launch {
                    patientRepository.getPatientByID(patientId)?.apply {
                        state = state.copy(
                            name = name,
                            age = age,
                            assignee = doctorAssigned,
                            prescription = prescription,
                            gender = gender
                        )
                        currentPatientId = patientId
                    }
                }
            }
        }
    }

}