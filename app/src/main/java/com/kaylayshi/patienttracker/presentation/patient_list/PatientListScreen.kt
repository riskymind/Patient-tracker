package com.kaylayshi.patienttracker.presentation.patient_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaylayshi.patienttracker.domain.model.Patient
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaylayshi.patienttracker.presentation.theme.PatientTrackerTheme


@Composable
fun PatientListScreen(
    onFabClick: () -> Unit,
    onItemClick: (Int?) -> Unit,
    viewModel: PatientListViewModel = hiltViewModel()
) {

    val patientList by viewModel.patientList.collectAsState()

    Scaffold(
        topBar = { AppBar() },
        floatingActionButton = { AppFab(onFabClick = onFabClick) }
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(patientList) { item: Patient ->
                PatientItem(
                    patient = item,
                    onItemClick = { onItemClick(item.id) },
                    onDeleteConfirm = { viewModel.onEvent(PatientListEvent.DeletePatient(item)) })
            }
        }

        if (patientList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add Patients Details\nby pressing add button.",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}


@Composable
fun AppBar() {
    TopAppBar(title = {
        Text(
            text = "Patient Tracker",
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
        )
    })
}


@Composable
fun AppFab(
    onFabClick: () -> Unit
) {
    FloatingActionButton(onClick = onFabClick) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Patient button")
    }
}

@Preview
@Composable
fun PatientListPreview() {
    PatientTrackerTheme {
        PatientListScreen(
            onFabClick = {},
            onItemClick = {}
        )
    }
}