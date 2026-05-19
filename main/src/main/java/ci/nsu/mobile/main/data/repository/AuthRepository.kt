package ci.nsu.mobile.main.data.repository

import ci.nsu.mobile.main.data.model.*
import ci.nsu.mobile.main.data.network.ApiService
import ci.nsu.mobile.main.data.storage.TokenManager

class AuthRepository(
    private val api: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun login(login: String, pass: String): Result<UserDto> = runCatching {
        val user = api.login(LoginRequest(login, pass))
        tokenManager.token = user.token
        user
    }

    suspend fun register(request: RegisterRequest): Result<Unit> = runCatching {
        api.register(request)
    }

    suspend fun getGroups(): Result<List<GroupDto>> = runCatching {
        api.getGroups()
    }

    suspend fun getUsers(): Result<List<UserDto>> = runCatching {
        api.getUsers()
    }

    fun logout() = tokenManager.clear()

    fun isAuthorized(): Boolean = tokenManager.token != null
}