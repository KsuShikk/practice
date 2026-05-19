package ci.nsu.mobile.main.data.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class UserDto(
    val id: Int? = null,
    val login: String,
    val email: String? = null,
    val token: String? = null
)