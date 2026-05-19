package ci.nsu.mobile.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ci.nsu.mobile.main.data.network.ApiService
import ci.nsu.mobile.main.data.network.AuthInterceptor
import ci.nsu.mobile.main.data.repository.AuthRepository
import ci.nsu.mobile.main.data.storage.TokenManager
import ci.nsu.mobile.main.ui.auth.LoginScreen
import ci.nsu.mobile.main.ui.auth.LoginViewModel
import ci.nsu.mobile.main.ui.auth.RegisterScreen
import ci.nsu.mobile.main.ui.auth.RegisterViewModel
import ci.nsu.mobile.main.ui.main.MainScreen
import ci.nsu.mobile.main.ui.main.MainViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация слоя данных
        val tokenManager = TokenManager(this)

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .addInterceptor(logging)
            .build()

        val json = Json { ignoreUnknownKeys = true }

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.200.160:8080/api/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val repository = AuthRepository(apiService, tokenManager)

        setContent {
            var currentScreen by remember {
                mutableStateOf(if (repository.isAuthorized()) "main" else "login")
            }

            MaterialTheme {
                when (currentScreen) {
                    "login" -> LoginScreen(
                        viewModel = LoginViewModel(repository),
                        onNavigateToRegister = { currentScreen = "register" },
                        onLoginSuccess = { currentScreen = "main" }
                    )
                    "register" -> RegisterScreen(
                        viewModel = RegisterViewModel(repository),
                        onBack = { currentScreen = "login" },
                        onSuccess = { currentScreen = "login" }
                    )
                    "main" -> MainScreen(
                        viewModel = MainViewModel(repository),
                        onLogout = { currentScreen = "login" }
                    )
                }
            }
        }
    }
}


