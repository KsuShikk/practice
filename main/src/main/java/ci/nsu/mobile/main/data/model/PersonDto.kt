package ci.nsu.mobile.main.data.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class PersonDto(
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val birthDate: String,
    val gender: String,
    val groupId: Int
)