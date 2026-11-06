import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TarefaViewModel(private val dao: TarefaDao) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val tarefas = dao.listarTodas()

    fun adicionarTarefa(titulo: String) {
        if (titulo.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            delay(800) // Delay simulado para visualizar a tela de loading

            dao.inserir(TarefaEntity(titulo = titulo))

            _isLoading.value = false
        }
    }
}