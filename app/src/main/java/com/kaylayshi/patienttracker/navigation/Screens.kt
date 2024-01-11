package com.kaylayshi.patienttracker.navigation

import com.kaylayshi.patienttracker.utils.Constants.PATIENT_DETAILS_ARGUMENT_KEY

sealed class Screens(val route: String) {
    object PatientList : Screens("patient_list_screen")
    object PatientDetails : Screens(
        "patient_detail_screen?$PATIENT_DETAILS_ARGUMENT_KEY={$PATIENT_DETAILS_ARGUMENT_KEY}"
    ) {
        fun passPatientId(patientId: Int? = null): String {
            return "patient_detail_screen?$PATIENT_DETAILS_ARGUMENT_KEY=$patientId"
        }
    }
}
