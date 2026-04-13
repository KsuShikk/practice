package ci.nsu.mobile.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Состояние экрана
data class TemperatureUiState(
    val celsius: String = "",
    val fahrenheit: String = ""
) {
    // Проверка на валидность (число или пустая строка)
    val isCelsiusValid: Boolean get() = celsius.isEmpty() || celsius.toDoubleOrNull() != null
    val isFahrenheitValid: Boolean get() = fahrenheit.isEmpty() || fahrenheit.toDoubleOrNull() != null
}

class TemperatureViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TemperatureUiState())
    val uiState: StateFlow<TemperatureUiState> = _uiState.asStateFlow()

    // Из Цельсия в Фаренгейт: F = C * 9/5 + 32
    fun onCelsiusChanged(newValue: String) {
        _uiState.update { currentState ->
            val fahrenheit = newValue.toDoubleOrNull()?.let {
                String.format("%.2f", it * 9 / 5 + 32)
            } ?: ""

            currentState.copy(
                celsius = newValue,
                fahrenheit = fahrenheit
            )
        }
    }

    // Из Фаренгейта в Цельсий: C = (F - 32) * 5/9
    fun onFahrenheitChanged(newValue: String) {
        _uiState.update { currentState ->
            val celsius = newValue.toDoubleOrNull()?.let {
                String.format("%.2f", (it - 32) * 5 / 9)
            } ?: ""

            currentState.copy(
                fahrenheit = newValue,
                celsius = celsius
            )
        }
    }
}