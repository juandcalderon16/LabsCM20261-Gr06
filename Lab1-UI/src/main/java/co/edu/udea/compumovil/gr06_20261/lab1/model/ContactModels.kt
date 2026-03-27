package co.edu.udea.compumovil.gr06_20261.lab1.model

data class PersonalData(
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "",         // "Male" / "Female" / ""
    val birthDate: String = "",      // formatted dd/MM/yyyy
    val educationLevel: String = ""  // optional
)

data class ContactData(
    val phone: String = "",
    val address: String = "",        // optional
    val email: String = "",
    val country: String = "",
    val city: String = ""            // optional
)