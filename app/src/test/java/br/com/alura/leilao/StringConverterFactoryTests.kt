package br.com.alura.leilao.api.retrofit.converter

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import java.lang.reflect.Type

class StringConverterFactoryTest {

    private lateinit var stringConverterFactory: StringConverterFactory

    @Mock
    private lateinit var retrofit: Retrofit

    @Mock
    private lateinit var type: Type

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        stringConverterFactory = StringConverterFactory()
    }

    @Test
    fun `should return converter when type is String`() {
        // given
        val type = String::class.java

        // when
        val converter = stringConverterFactory.requestBodyConverter(type, arrayOf(), arrayOf(), retrofit)

        // then
        val expectedBody = "testString".toRequestBody("text/plain".toMediaTypeOrNull())
        assertThat(converter?.convert("testString"), `is`(expectedBody))
    }

    @Test
    fun `should return null when type is not String`() {
        // when
        val converter = stringConverterFactory.requestBodyConverter(type, arrayOf(), arrayOf(), retrofit)

        // then
        assertThat(converter, nullValue())
    }
}