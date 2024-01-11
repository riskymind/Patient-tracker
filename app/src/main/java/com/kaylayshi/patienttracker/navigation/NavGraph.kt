package com.kaylayshi.patienttracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kaylayshi.patienttracker.presentation.patient_detail.PatientDetailsScreen
import com.kaylayshi.patienttracker.presentation.patient_list.PatientListScreen
import com.kaylayshi.patienttracker.utils.Constants

@Composable
fun NavGraphSetup(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screens.PatientList.route) {
        composable(route = Screens.PatientList.route) {
            PatientListScreen(
                onFabClick = { navController.navigate(Screens.PatientDetails.route) },
                onItemClick = {
                    navController.navigate(Screens.PatientDetails.passPatientId(it))
                }
            )
        }

        composable(
            route = Screens.PatientDetails.route,
            arguments = listOf(navArgument(name = Constants.PATIENT_DETAILS_ARGUMENT_KEY) {
                type = NavType.IntType
                defaultValue = -1
            })
        ) {
            PatientDetailsScreen(
                onBackClick = { navController.navigateUp() },
                onSuccessSave = { navController.navigateUp() }
            )
        }
    }

}