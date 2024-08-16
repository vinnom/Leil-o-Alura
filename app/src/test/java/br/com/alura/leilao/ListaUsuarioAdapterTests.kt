import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ListaUsuarioAdapterTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var parent: ViewGroup

    @Mock
    private lateinit var viewHolder: RecyclerView.ViewHolder

    @Mock
    private lateinit var textView: TextView

    private lateinit var adapter: ListaUsuarioAdapter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        adapter = ListaUsuarioAdapter(context)
    }

    @Test
    fun `should return correct number of items`() {
        val users = listOf(Usuario("User1"), Usuario("User2"))
        adapter.adiciona(users)

        assertThat(adapter.itemCount, equalTo(2))
    }

    @Test
    fun `should add user correctly`() {
        val user = Usuario("User1")
        adapter.adiciona(user)

        assertThat(adapter.itemCount, equalTo(1))
    }

    @Test
    fun `should bind view holder correctly`() {
        val user = Usuario("User1")
        adapter.adiciona(user)

        val viewHolder = Mockito.mock(ListaUsuarioAdapter.ViewHolder::class.java)
        Mockito.doNothing().`when`(viewHolder).vincula(user)

        adapter.onBindViewHolder(viewHolder, 0)

        Mockito.verify(viewHolder).vincula(user)
    }
}