import br.com.alura.leilao.ui.activity.CHAVE_LEILAO
import org.junit.Test
import org.junit.Assert.assertThat
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.core.IsNull.notNullValue

@RunWith(MockitoJUnitRunner::class)
class LeilaoActivityTest {

    // Test to check if the key is correct
    @Test
    fun shouldPass_WhenKeyIsCorrect() {
        assertThat(CHAVE_LEILAO, equalTo("leilao"))
    }

    // Test to check if the key is not null
    @Test
    fun shouldPass_WhenKeyIsNotNull() {
        assertThat(CHAVE_LEILAO, notNullValue())
    }

    // Test to check if the key is not empty
    @Test
    fun shouldPass_WhenKeyIsNotEmpty() {
        assertThat(CHAVE_LEILAO.isNotEmpty(), equalTo(true))
    }

    // Test to mock the LeilaoActivity class
    @Test
    fun shouldPass_WhenLeilaoActivityIsMocked() {
        val mockedLeilaoActivity = mock(LeilaoActivity::class.java)
        verify(mockedLeilaoActivity).notify()
    }
}