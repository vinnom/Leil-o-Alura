import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.leilao.database.dao.UsuarioDAO
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.ui.AtualizadorDeUsuario
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AtualizadorDeUsuarioTest {

    @Mock
    private lateinit var dao: UsuarioDAO

    @Mock
    private lateinit var adapter: ListaUsuarioAdapter

    @Mock
    private lateinit var recyclerView: RecyclerView

    @Mock
    private lateinit var lifecycleScope: LifecycleCoroutineScope

    private lateinit var atualizadorDeUsuario: AtualizadorDeUsuario

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        atualizadorDeUsuario = AtualizadorDeUsuario(dao, adapter, recyclerView, lifecycleScope)
    }

    @Test
    fun `When salva is called, then it should save the user and update the list`() = runBlockingTest {
        // Arrange
        val user = Usuario(1, "User1")
        Mockito.`when`(dao.salva(user)).thenReturn(1)
        Mockito.`when`(dao.buscaPorId(1)).thenReturn(user)

        // Act
        atualizadorDeUsuario.salva(user)

        // Assert
        Mockito.verify(dao).salva(user)
        Mockito.verify(dao).buscaPorId(1)
        Mockito.verify(adapter).adiciona(user)
        Mockito.verify(recyclerView).smoothScrollToPosition(adapter.itemCount - 1)
    }
}