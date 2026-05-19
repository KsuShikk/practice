package ci.nsu.mobile.main.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import ci.nsu.mobile.main.data.model.PersonDto
import ci.nsu.mobile.main.data.model.RegisterRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel, onBack: () -> Unit, onSuccess: () -> Unit) {
    val groups by viewModel.groups.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var patronymic by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var selectedGroup by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Регистрация", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(firstName, { firstName = it }, label = { Text("Имя") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(lastName, { lastName = it }, label = { Text("Фамилия") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(patronymic, { patronymic = it }, label = { Text("Отчество") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(dateOfBirth, { dateOfBirth = it }, label = { Text("Дата рождения (ГГГГ-ММ-ДД)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(gender, { gender = it }, label = { Text("Пол") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(phoneNumber, { phoneNumber = it }, label = { Text("Телефон") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(8.dp))

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = groups.find { it.id == selectedGroup }?.name ?: "Выберите группу",
                onValueChange = {},
                readOnly = true,
                label = { Text("Группа") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                groups.forEach { group ->
                    DropdownMenuItem(
                        text = { Text(group.name) },
                        onClick = {
                            selectedGroup = group.id
                            expanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(login, { login = it }, label = { Text("Логин") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(pass, { pass = it }, label = { Text("Пароль") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else {
            Button(
                onClick = {
                    val request = RegisterRequest(
                        login = login, password = pass, email = email, phoneNumber = phoneNumber,
                        person = PersonDto(firstName, lastName, patronymic, dateOfBirth, gender, selectedGroup ?: 0)
                    )
                    viewModel.register(request, onSuccess)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedGroup != null && login.isNotBlank() && pass.isNotBlank()
            ) {
                Text("Зарегистрироваться")
            }
        }
        TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Назад к логину") }
    }
}