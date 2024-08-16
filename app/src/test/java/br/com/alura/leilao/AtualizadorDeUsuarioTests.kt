package br.com.alura.leilao.ui

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.leilao.database.dao.UsuarioDAO
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class AtualizadorDeUsuarioTest {

    @Mock
    private lateinit var dao: UsuarioDAO

    @Mock
    private lateinit var adapter: ListaUsuarioAdapter

    @Mock
    private lateinit var recyclerView: RecyclerView

    private lateinit var lifecycleScope: LifecycleCoroutineScope

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var atualizador: AtualizadorDeUsuario

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        lifecycleScope = LifecycleCoroutineScope(testDispatcher)
        atualizador = AtualizadorDeUsuario(dao, adapter, recyclerView, lifecycleScope)
    }

    @Test
    fun `when save, should add user to list`() = testDispatcher.runBlockingTest {
        // Arrange
        val usuario = Usuario("Usuario1")
        `when`(dao.salva(usuario)).thenReturn(1L)
        `when`(dao.buscaPorId(1L)).thenReturn(usuario)

        // Act
        atualizador.salva(usuario)

        // Assert
        verify(dao).salva(usuario)
        verify(dao).buscaPorId(1L)
        verify(adapter).adiciona(usuario)
        verify(recyclerView).smoothScrollToPosition(adapter.itemCount - 1)
    }

    @Test
    fun `when save, should not add user to list if user not found`() = testDispatcher.runBlockingTest {
        // Arrange
        val usuario = Usuario("Usuario1")
        `when`(dao.salva(usuario)).thenReturn(1L)
        `when`(dao.buscaPorId(1L)).thenReturn(null)

        // Act
        atualizador.salva(usuario)

        // Assert
        verify(dao).salva(usuario)
        verify(dao).buscaPorId(1L)
        verify(adapter, never()).adiciona(usuario)
        verify(recyclerView, never()).smoothScrollToPosition(adapter.itemCount - 1)
    }
}