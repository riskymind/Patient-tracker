package com.kaylayshi.patienttracker.presentation.patient_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaylayshi.patienttracker.domain.model.Patient
import com.kaylayshi.patienttracker.presentation.theme.PatientTrackerTheme

@Composable
fun PatientItem(
    patient: Patient,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
    onDeleteConfirm: () -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteDialog(
            title = "Delete",
            message = "Are you sure, you want to delete ${patient.name}",
            onDismiss = { showDialog = false },
            onConfirm = onDeleteConfirm
        )
    }

    Card(
        elevation = 4.dp,
        modifier = modifier.clickable { onItemClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = patient.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Assigned to -> ${patient.doctorAssigned}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle1
                )
            }

            IconButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete button")
            }

        }
    }

}

@Preview
@Composable
fun PatientItemPreview() {
    PatientTrackerTheme {
        val patient = Patient(
            name = "kelechi",
            age = "4",
            gender = 2,
            doctorAssigned = "kamsi",
            prescription = "e",
            id = 0
        )
        PatientItem(patient = patient, onItemClick = { /*TODO*/ }) {

        }
    }
}