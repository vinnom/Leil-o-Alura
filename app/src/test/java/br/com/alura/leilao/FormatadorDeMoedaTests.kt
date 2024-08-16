import br.com.alura.leilao.model.formatter.FormatadorDeMoeda
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FormatadorDeMoedaTest {

    @Test
    fun shouldReturnFormattedValueWhenFormatIsCalled() {
        // Arrange
        val expectedValue = "R$ 100,00"
        val valueToFormat = 100.0

        // Act
        val formattedValue = FormatadorDeMoeda.formata(valueToFormat)

        // Assert
        assertThat(formattedValue, equalTo(expectedValue))
    }

    @Test
    fun shouldReturnFormattedValueWithDecimalWhenFormatIsCalled() {
        // Arrange
        val expectedValue = "R$ 100,50"
        val valueToFormat = 100.50

        // Act
        val formattedValue = FormatadorDeMoeda.formata(valueToFormat)

        // Assert
        assertThat(formattedValue, equalTo(expectedValue))
    }

    @Test
    fun shouldReturnFormattedValueWithThousandsSeparatorWhenFormatIsCalled() {
        // Arrange
        val expectedValue = "R$ 1.000,00"
        val valueToFormat = 1000.0

        // Act
        val formattedValue = FormatadorDeMoeda.formata(valueToFormat)

        // Assert
        assertThat(formattedValue, equalTo(expectedValue))
    }
}