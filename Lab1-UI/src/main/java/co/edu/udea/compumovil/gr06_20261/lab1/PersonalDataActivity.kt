package co.edu.udea.compumovil.gr06_20261.lab1

import android.app.DatePickerDialog
import android.os.Bundle
import java.util.Calendar

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

import co.edu.udea.compumovil.gr06_20261.lab1.ui.theme.LabsCM20261Gr06Theme

class PersonalDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabsCM20261Gr06Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PersonalDataScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PersonalDataScreen(modifier: Modifier = Modifier) {

    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var escolaridad by remember { mutableStateOf("Seleccione") }
    var expanded by remember { mutableStateOf(false) }

    val opciones = listOf("Primaria", "Secundaria", "Universidad")

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // ✅ DATE PICKER CORRECTO
    val datePicker = DatePickerDialog(
        context,
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            fechaNacimiento = "$day/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(modifier = modifier.padding(16.dp)) {

        // NOMBRES *
        OutlinedTextField(
            value = nombres,
            onValueChange = { nombres = it },
            label = { Text("Nombres *") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // APELLIDOS *
        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("Apellidos *") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // SEXO
        Text("Sexo")

        Row {
            RadioButton(
                selected = sexo == "Masculino",
                onClick = { sexo = "Masculino" }
            )
            Text("Masculino")

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = sexo == "Femenino",
                onClick = { sexo = "Femenino" }
            )
            Text("Femenino")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // FECHA *
        Button(onClick = {
            datePicker.show()
        }) {
            Text("Seleccionar fecha de nacimiento *")
        }

        Text(
            text = if (fechaNacimiento.isEmpty()) "No seleccionada" else fechaNacimiento
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ESCOLARIDAD (Spinner)
        @OptIn(ExperimentalMaterial3Api::class)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = escolaridad,
                onValueChange = {},
                readOnly = true,
                label = { Text("Grado de escolaridad") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            escolaridad = opcion
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BOTÓN GUARDAR
        Button(onClick = {
            if (nombres.isEmpty() || apellidos.isEmpty() || fechaNacimiento.isEmpty()) {
                println("Faltan campos obligatorios")
            } else {
                println("Formulario válido")
            }
        }) {
            Text("Guardar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonalDataPreview() {
    LabsCM20261Gr06Theme {
        PersonalDataScreen()
    }
}