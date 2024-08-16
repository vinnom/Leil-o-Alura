package br.com.alura.leilao.ui.recyclerview.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.leilao.model.Usuario
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ListaUsuarioAdapterTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var parent: ViewGroup

    @Mock
    private lateinit var viewHolder: ListaUsuarioAdapter.ViewHolder

    @Mock
    private lateinit var textView: TextView

    private lateinit var adapter: ListaUsuarioAdapter
    private lateinit var usuario: Usuario

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        adapter = ListaUsuarioAdapter(context)
        usuario = Usuario("Usuario1")
    }

    @Test
    fun `test getItemCount when list is empty`() = runBlockingTest {
        assertThat(adapter.itemCount, equalTo(0))
    }

    @Test
    fun `test getItemCount when list has one item`() = runBlockingTest {
        adapter.adiciona(usuario)
        assertThat(adapter.itemCount, equalTo(1))
    }

    @Test
    fun `test onCreateViewHolder`() = runBlockingTest {
        `when`(parent.context).thenReturn(context)
        val viewHolder = adapter.onCreateViewHolder(parent, 0)
        assertThat(viewHolder, equalTo(viewHolder))
    }

    @Test
    fun `test onBindViewHolder`() = runBlockingTest {
        adapter.adiciona(usuario)
        adapter.onBindViewHolder(viewHolder, 0)
        assertThat(viewHolder.toString(), equalTo(usuario.toString()))
    }

    @Test
    fun `test adiciona with one user`() = runBlockingTest {
        adapter.adiciona(usuario)
        assertThat(adapter.itemCount, equalTo(1))
    }

    @Test
    fun `test adiciona with list of users`() = runBlockingTest {
        val usuarios = listOf(usuario, Usuario("Usuario2"))
        adapter.adiciona(usuarios)
        assertThat(adapter.itemCount, equalTo(2))
    }
}