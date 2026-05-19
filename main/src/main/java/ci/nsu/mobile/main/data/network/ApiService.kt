package ci.nsu.mobile.main.data.network

import ci.nsu.mobile.main.data.model.*
import retrofit2.http.*

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): UserDto

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest)

    @GET("groups")
    suspend fun getGroups(): List<GroupDto>

    @GET("users")
    suspend fun getUsers(): List<UserDto>
}