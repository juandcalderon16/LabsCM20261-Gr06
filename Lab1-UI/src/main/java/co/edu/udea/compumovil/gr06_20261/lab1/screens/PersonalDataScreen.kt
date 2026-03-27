package co.edu.udea.compumovil.gr06_20261.lab1.screens

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.udea.compumovil.gr06_20261.lab1.R
import co.edu.udea.compumovil.gr06_20261.lab1.viewmodel.ContactViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataScreen(
    viewModel: ContactViewModel,
    onNext: () -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Education dropdown
    val educationLevels = stringArrayResource(R.array.education_levels)
    var expanded by remember { mutableStateOf(false) }

    // Date Picker
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            viewModel.birthDate = "$dayOfMonth/${month + 1}/$year"
            viewModel.birthDateError = false
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.personal_info_title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = if (isLandscape) 32.dp else 20.dp, vertical = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // ── First Name ──────────────────────────────────────────────
            OutlinedTextField(
                value = viewModel.firstName,
                onValueChange = { viewModel.firstName = it; viewModel.firstNameError = false },
                label = { Text(stringResource(R.string.first_name_label) + " *") },
                placeholder = { Text(stringResource(R.string.first_name_hint)) },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                isError = viewModel.firstNameError,
                supportingText = {
                    if (viewModel.firstNameError)
                        Text(
                            stringResource(R.string.field_required),
                            color = MaterialTheme.colorScheme.error
                        )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ── Last Name ───────────────────────────────────────────────
            OutlinedTextField(
                value = viewModel.lastName,
                onValueChange = { viewModel.lastName = it; viewModel.lastNameError = false },
                label = { Text(stringResource(R.string.last_name_label) + " *") },
                placeholder = { Text(stringResource(R.string.last_name_hint)) },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                isError = viewModel.lastNameError,
                supportingText = {
                    if (viewModel.lastNameError)
                        Text(
                            stringResource(R.string.field_required),
                            color = MaterialTheme.colorScheme.error
                        )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ── Gender Selection ────────────────────────────────────────
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Group,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        stringResource(R.string.gender_label),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = viewModel.gender == "Masculino",
                        onClick = { viewModel.gender = "Masculino" }
                    )
                    Text(stringResource(R.string.gender_male))
                    Spacer(Modifier.width(16.dp))
                    RadioButton(
                        selected = viewModel.gender == "Femenino",
                        onClick = { viewModel.gender = "Femenino" }
                    )
                    Text(stringResource(R.string.gender_female))
                }
            }

            // ── Birth Date ──────────────────────────────────────────────
            Column {
                OutlinedTextField(
                    value = viewModel.birthDate,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.birth_date_label) + " *") },
                    placeholder = { Text(stringResource(R.string.birth_date_hint)) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.CalendarToday,
                            null,
                            modifier = Modifier.padding(4.dp)
                        )
                    },
                    isError = viewModel.birthDateError,
                    supportingText = {
                        if (viewModel.birthDateError)
                            Text(
                                stringResource(R.string.field_required),
                                color = MaterialTheme.colorScheme.error
                            )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Button(
                            onClick = { datePickerDialog.show() },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier.padding(end = 4.dp).height(36.dp)
                        ) {
                            Text(stringResource(R.string.change_button), fontSize = 12.sp)
                        }
                    }
                )
            }

            // ── Education Level ─────────────────────────────────────────
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = viewModel.educationLevel,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.education_label)) },
                    placeholder = { Text(stringResource(R.string.education_hint)) },
                    leadingIcon = { Icon(Icons.Default.School, null) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    educationLevels.forEach { level ->
                        DropdownMenuItem(
                            text = { Text(level) },
                            onClick = {
                                viewModel.educationLevel = level
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    if (viewModel.validatePersonalData()) {
                        onNext()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    stringResource(R.string.next_button),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
