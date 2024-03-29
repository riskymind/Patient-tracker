package com.kaylayshi.patienttracker.presentation.patient_detail

import android.widget.RadioGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaylayshi.patienttracker.presentation.theme.PatientTrackerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PatientDetailsScreen(
    onBackClick: () -> Unit,
    onSuccessSave: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                UiEvent.SaveNote -> {
                    onSuccessSave()
                    Toast.makeText(context, "Successfully Save", Toast.LENGTH_SHORT).show()
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    })

    LaunchedEffect(key1 = Unit, block = {
        delay(500)
        focusRequester.requestFocus()
    })

    val state = viewModel.state

    Scaffold(
        topBar = {
            TopBar { onBackClick() }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .padding(it)
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = state.name,
                onValueChange = { newValue ->
                    viewModel.onAction(DetailEvents.EnterName(newValue))
                },
                label = { Text(text = "Name") },
                textStyle = MaterialTheme.typography.body1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = state.age,
                    onValueChange = { newValue ->
                        viewModel.onAction(DetailEvents.EnterAge(newValue))
                    },
                    label = { Text(text = "Age") },
                    textStyle = MaterialTheme.typography.body1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                RadioGroup(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Male",
                    selected = state.gender == 1,
                    onClick = {
                        viewModel.onAction(DetailEvents.SelectMale)
                    }
                )
                RadioGroup(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Female",
                    selected = state.gender == 2,
                    onClick = { viewModel.onAction(DetailEvents.SelectFemale) }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.assignee,
                onValueChange = { newValue ->
                    viewModel.onAction(DetailEvents.EnterAssignee(newValue))
                },
                label = { Text(text = "Assigned Doctor's Name") },
                textStyle = MaterialTheme.typography.body1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = state.prescription,
                onValueChange = { newValue ->
                    viewModel.onAction(DetailEvents.EnterPrescription(newValue))
                },
                label = { Text(text = "Prescription") },
                textStyle = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.onAction(DetailEvents.Save)
                }
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
            }
        }
    }

}


@Composable
fun TopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Patient's Details Screen",
                style = MaterialTheme.typography.h5
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}


@Composable
private fun RadioGroup(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary
            )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.body1
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PatientDetailPreview() {
    PatientTrackerTheme {}
}