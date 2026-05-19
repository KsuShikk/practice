package ci.nsu.mobile.main.data.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class LoginRequest(
    val login: String,
    val password: String
)