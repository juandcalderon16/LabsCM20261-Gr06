package co.edu.udea.compumovil.gr06_20261.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.edu.udea.compumovil.gr06_20261.lab1.screens.ContactDataScreen
import co.edu.udea.compumovil.gr06_20261.lab1.screens.PersonalDataScreen
import co.edu.udea.compumovil.gr06_20261.lab1.screens.SummaryScreen
import co.edu.udea.compumovil.gr06_20261.lab1.ui.theme.ContactAppTheme
import co.edu.udea.compumovil.gr06_20261.lab1.viewmodel.ContactViewModel

object Routes {
    const val PERSONAL_DATA = "personal_data"
    const val CONTACT_DATA = "contact_data"
    const val SUMMARY = "summary"
}

class MainActivity : ComponentActivity() {

    private val viewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactAppNavigation(viewModel)
                }
            }
        }
    }
}

@Composable
fun ContactAppNavigation(viewModel: ContactViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.PERSONAL_DATA
    ) {
        composable(Routes.PERSONAL_DATA) {
            PersonalDataScreen(
                viewModel = viewModel,
                onNext = {
                    navController.navigate(Routes.CONTACT_DATA)
                }
            )
        }

        composable(Routes.CONTACT_DATA) {
            ContactDataScreen(
                viewModel = viewModel,
                onFinish = {
                    navController.navigate(Routes.SUMMARY) {
                        popUpTo(Routes.PERSONAL_DATA) { inclusive = false }
                    }
                }
            )
        }

        composable(Routes.SUMMARY) {
            SummaryScreen(
                viewModel = viewModel,
                onRestart = {
                    viewModel.firstName = ""
                    viewModel.lastName = ""
                    viewModel.gender = ""
                    viewModel.birthDate = ""
                    viewModel.educationLevel = ""
                    viewModel.phone = ""
                    viewModel.address = ""
                    viewModel.email = ""
                    viewModel.country = ""
                    viewModel.city = ""
                    navController.navigate(Routes.PERSONAL_DATA) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}