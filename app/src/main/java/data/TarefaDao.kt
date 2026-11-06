package data

import TarefaEntity

class TarefaDaoimport androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TarefaDao {
    @Insert
    suspend fun inserir(tarefa: TarefaEntity)

    @Query("SELECT * FROM tarefas ORDER BY id DESC")
    fun listarTodas(): Flow<List<TarefaEntity>>
} {
}