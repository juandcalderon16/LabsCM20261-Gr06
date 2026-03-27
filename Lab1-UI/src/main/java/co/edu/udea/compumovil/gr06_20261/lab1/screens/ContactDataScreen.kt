package co.edu.udea.compumovil.gr06_20261.lab1.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.udea.compumovil.gr06_20261.lab1.R
import co.edu.udea.compumovil.gr06_20261.lab1.utils.LocationData
import co.edu.udea.compumovil.gr06_20261.lab1.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDataScreen(
    viewModel: ContactViewModel,
    onFinish: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Country autocomplete
    var countryQuery by remember { mutableStateOf(viewModel.country) }
    var countryExpanded by remember { mutableStateOf(false) }
    val filteredCountries = remember(countryQuery) {
        if (countryQuery.isBlank()) LocationData.latinAmericanCountries
        else LocationData.latinAmericanCountries.filter {
            it.contains(countryQuery, ignoreCase = true)
        }
    }

    // City autocomplete
    var cityQuery by remember { mutableStateOf(viewModel.city) }
    var cityExpanded by remember { mutableStateOf(false) }
    val filteredCities = remember(cityQuery) {
        if (cityQuery.isBlank()) LocationData.colombianCities
        else LocationData.colombianCities.filter {
            it.contains(cityQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.contact_info_title),
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

            // ── Phone ─────────────────────────────────────────────────────
            OutlinedTextField(
                value = viewModel.phone,
                onValueChange = { viewModel.phone = it; viewModel.phoneError = false },
                label = { Text(stringResource(R.string.phone_label) + " *") },
                placeholder = { Text(stringResource(R.string.phone_hint)) },
                leadingIcon = { Icon(Icons.Default.Phone, null) },
                isError = viewModel.phoneError,
                supportingText = {
                    if (viewModel.phoneError)
                        Text(
                            stringResource(R.string.field_required),
                            color = MaterialTheme.colorScheme.error
                        )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ── Address (optional) ────────────────────────────────────────
            OutlinedTextField(
                value = viewModel.address,
                onValueChange = { viewModel.address = it },
                label = { Text(stringResource(R.string.address_label)) },
                placeholder = { Text(stringResource(R.string.address_hint)) },
                leadingIcon = { Icon(Icons.Default.Home, null) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    autoCorrect = false,
                    capitalization = KeyboardCapitalization.None,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ── Email ─────────────────────────────────────────────────────
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = {
                    viewModel.email = it
                    viewModel.emailError = false
                    viewModel.emailFormatError = false
                },
                label = { Text(stringResource(R.string.email_label) + " *") },
                placeholder = { Text(stringResource(R.string.email_hint)) },
                leadingIcon = { Icon(Icons.Default.Email, null) },
                isError = viewModel.emailError || viewModel.emailFormatError,
                supportingText = {
                    when {
                        viewModel.emailError ->
                            Text(
                                stringResource(R.string.field_required),
                                color = MaterialTheme.colorScheme.error
                            )

                        viewModel.emailFormatError ->
                            Text(
                                stringResource(R.string.invalid_email),
                                color = MaterialTheme.colorScheme.error
                            )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ── Country Autocomplete ──────────────────────────────────────
            ExposedDropdownMenuBox(
                expanded = countryExpanded && filteredCountries.isNotEmpty(),
                onExpandedChange = { countryExpanded = it }
            ) {
                OutlinedTextField(
                    value = countryQuery,
                    onValueChange = {
                        countryQuery = it
                        viewModel.country = it
                        viewModel.countryError = false
                        countryExpanded = true
                    },
                    label = { Text(stringResource(R.string.country_label) + " *") },
                    placeholder = { Text(stringResource(R.string.country_hint)) },
                    leadingIcon = { Icon(Icons.Default.Public, null) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = countryExpanded) },
                    isError = viewModel.countryError,
                    supportingText = {
                        if (viewModel.countryError)
                            Text(
                                stringResource(R.string.field_required),
                                color = MaterialTheme.colorScheme.error
                            )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    }),
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    singleLine = true
                )
                ExposedDropdownMenu(
                    expanded = countryExpanded && filteredCountries.isNotEmpty(),
                    onDismissRequest = { countryExpanded = false }
                ) {
                    filteredCountries.take(10).forEach { c ->
                        DropdownMenuItem(
                            text = { Text(c) },
                            onClick = {
                                countryQuery = c
                                viewModel.country = c
                                viewModel.countryError = false
                                countryExpanded = false
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        )
                    }
                }
            }

            // ── City Autocomplete (optional) ──────────────────────────────
            ExposedDropdownMenuBox(
                expanded = cityExpanded && filteredCities.isNotEmpty(),
                onExpandedChange = { cityExpanded = it }
            ) {
                OutlinedTextField(
                    value = cityQuery,
                    onValueChange = {
                        cityQuery = it
                        viewModel.city = it
                        cityExpanded = true
                    },
                    label = { Text(stringResource(R.string.city_label)) },
                    placeholder = { Text(stringResource(R.string.city_hint)) },
                    leadingIcon = { Icon(Icons.Default.LocationCity, null) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = cityExpanded) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    singleLine = true
                )
                ExposedDropdownMenu(
                    expanded = cityExpanded && filteredCities.isNotEmpty(),
                    onDismissRequest = { cityExpanded = false }
                ) {
                    filteredCities.take(10).forEach { city ->
                        DropdownMenuItem(
                            text = { Text(city) },
                            onClick = {
                                cityQuery = city
                                viewModel.city = city
                                cityExpanded = false
                                focusManager.clearFocus()
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            Button(
                onClick = {
                    focusManager.clearFocus()
                    if (viewModel.validateContactData()) {
                        Log.d("ContactApp", viewModel.logPersonalData())
                        Log.d("ContactApp", viewModel.logContactData())
                        onFinish()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    stringResource(R.string.finish_button),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
