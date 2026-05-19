package ci.nsu.mobile.main.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel, onLogout: () -> Unit) {
    val users by viewModel.users.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Пользователи системы") },
                actions = {
                    TextButton(onClick = { viewModel.logout(onLogout) }) {
                        Text("Выйти", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(users) { user ->
                ListItem(
                    headlineContent = { Text(user.login) },
                    supportingContent = { Text(user.email ?: "Email отсутствует") }
                )
                HorizontalDivider()
            }
        }
    }
}