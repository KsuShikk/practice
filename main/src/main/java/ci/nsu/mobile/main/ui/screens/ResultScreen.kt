package ci.nsu.mobile.main.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ci.nsu.mobile.main.viewmodel.DepositViewModel

@Composable
fun ResultScreen(navController: NavController, viewModel: DepositViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Результат", style = MaterialTheme.typography.headlineSmall)
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Итог: ${String.format("%.2f", viewModel.finalAmount)} ₽")
                Text("Доход: ${String.format("%.2f", viewModel.interestEarned)} ₽")
            }
        }
        Button(onClick = { viewModel.saveToDb(); navController.navigate("history") }, modifier = Modifier.fillMaxWidth()) {
            Text("Сохранить")
        }
        TextButton(onClick = { navController.popBackStack("main", false) }, modifier = Modifier.fillMaxWidth()) {
            Text("В начало")
        }
    }
}