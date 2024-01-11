package com.kaylayshi.patienttracker.presentation.patient_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaylayshi.patienttracker.domain.model.Patient
import com.kaylayshi.patienttracker.domain.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientListViewModel @Inject constructor(
    private val patientRepository: PatientRepository
) : ViewModel() {

    private var _patientList = MutableStateFlow<List<Patient>>(emptyList())
    val patientList = _patientList.asStateFlow()

    var isLoading by mutableStateOf(false)

    init {
        viewModelScope.launch {
            isLoading = true
            patientRepository.getAllPatients().collect {
                _patientList.value = it
            }
            isLoading = false
        }
    }

    fun onEvent(event: PatientListEvent) {
        when (event) {
            is PatientListEvent.DeletePatient -> deletePatient(event.patient)
            PatientListEvent.GetPatients -> TODO()
            PatientListEvent.NavigateToDetails -> TODO()
        }
    }

    private fun deletePatient(patient: Patient) = viewModelScope.launch {
        patientRepository.deletePatient(patient)
    }
}