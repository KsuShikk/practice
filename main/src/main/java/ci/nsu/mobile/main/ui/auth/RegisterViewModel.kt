package ci.nsu.mobile.main.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ci.nsu.mobile.main.data.model.GroupDto
import ci.nsu.mobile.main.data.model.RegisterRequest
import ci.nsu.mobile.main.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _groups = MutableStateFlow<List<GroupDto>>(emptyList())
    val groups = _groups.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadGroups()
    }

    private fun loadGroups() {
        viewModelScope.launch {
            repository.getGroups().onSuccess { _groups.value = it }
        }
    }

    fun register(request: RegisterRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.register(request)
                .onSuccess { onSuccess() }
            _isLoading.value = false
        }
    }
}