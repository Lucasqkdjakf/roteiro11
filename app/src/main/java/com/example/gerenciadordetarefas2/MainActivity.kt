import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialização do Banco
        val db = AppDatabase.getDatabase(applicationContext)
        val dao = db.tarefaDao()

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Inicialização do ViewModel usando Factory
                    val viewModel: TarefaViewModel = viewModel(
                        factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return TarefaViewModel(dao) as T
                            }
                        }
                    )
                    TelaTarefas(viewModel)
                }
            }
        }
    }
}

@Composable
fun TelaTarefas(viewModel: TarefaViewModel) {
    val isLoading by viewModel.isLoading.collectAsState()
    val tarefas by viewModel.tarefas.collectAsState(initial = emptyList())
    var titulo by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Nova Tarefa") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.adicionarTarefa(titulo)
                    titulo = "" // Limpa o campo
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && titulo.isNotBlank()
            ) {
                Text("Salvar Tarefa")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(tarefas) { tarefa ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = tarefa.titulo,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        // Indicador de Carregamento Sobreposto
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}