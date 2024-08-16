import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import java.lang.reflect.Type

@RunWith(MockitoJUnitRunner::class)
class StringConverterFactoryTest {

    private val converterFactory = StringConverterFactory()
    private val retrofit = Retrofit.Builder().baseUrl("http://localhost").build()

    @Test
    fun `should return converter when type is String`() {
        // Arrange
        val type: Type = String::class.java
        val expected = "test".toRequestBody("text/plain".toMediaTypeOrNull())

        // Act
        val converter = converterFactory.requestBodyConverter(type, emptyArray(), emptyArray(), retrofit)
        val actual = converter?.convert("test")

        // Assert
        assertThat(actual, `is`(expected))
    }

    @Test
    fun `should return null when type is not String`() {
        // Arrange
        val type: Type = Double::class.javaObjectType

        // Act
        val converter = converterFactory.requestBodyConverter(type, emptyArray(), emptyArray(), retrofit)

        // Assert
        assertThat(converter, `is`<Converter<*, RequestBody>?>(null))
    }
}