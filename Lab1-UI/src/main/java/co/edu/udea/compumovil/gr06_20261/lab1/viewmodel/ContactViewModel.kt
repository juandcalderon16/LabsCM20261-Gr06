package co.edu.udea.compumovil.gr06_20261.lab1.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ContactViewModel : ViewModel() {

    // ─── Personal Data fields ───────────────────────────────────────────────
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var gender by mutableStateOf("")          // "Male" | "Female" | ""
    var birthDate by mutableStateOf("")       // "dd/MM/yyyy"
    var educationLevel by mutableStateOf("")  // optional

    // ─── Contact Data fields ─────────────────────────────────────────────────
    var phone by mutableStateOf("")
    var address by mutableStateOf("")         // optional
    var email by mutableStateOf("")
    var country by mutableStateOf("")
    var city by mutableStateOf("")            // optional

    // ─── Validation errors ──────────────────────────────────────────────────
    var firstNameError by mutableStateOf(false)
    var lastNameError by mutableStateOf(false)
    var birthDateError by mutableStateOf(false)
    var phoneError by mutableStateOf(false)
    var emailError by mutableStateOf(false)
    var countryError by mutableStateOf(false)
    var emailFormatError by mutableStateOf(false)

    fun validatePersonalData(): Boolean {
        firstNameError = firstName.isBlank()
        lastNameError = lastName.isBlank()
        birthDateError = birthDate.isBlank()
        return !firstNameError && !lastNameError && !birthDateError
    }

    fun validateContactData(): Boolean {
        phoneError = phone.isBlank()
        emailError = email.isBlank()
        emailFormatError = email.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        countryError = country.isBlank()
        return !phoneError && !emailError && !emailFormatError && !countryError
    }

    fun logPersonalData(): String {
        val sb = StringBuilder()
        sb.appendLine("Información personal:")
        sb.appendLine("$firstName $lastName")
        if (gender.isNotBlank()) sb.appendLine(gender)
        if (birthDate.isNotBlank()) sb.appendLine("Nació el $birthDate")
        if (educationLevel.isNotBlank()) sb.appendLine(educationLevel)
        return sb.toString().trim()
    }

    fun logContactData(): String {
        val sb = StringBuilder()
        sb.appendLine("Información de contacto:")
        sb.appendLine("Teléfono: $phone")
        if (address.isNotBlank()) sb.appendLine("Dirección: $address")
        sb.appendLine("Email: $email")
        sb.appendLine("País: $country")
        if (city.isNotBlank()) sb.appendLine("Ciudad: $city")
        return sb.toString().trim()
    }
}