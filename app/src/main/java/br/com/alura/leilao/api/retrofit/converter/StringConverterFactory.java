package br.com.alura.leilao.api.retrofit.converter;

import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class StringConverterFactory extends Converter.Factory{

   private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");

   @Nullable
   @Override
   public Converter<?, RequestBody> requestBodyConverter(
      Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit)
   {
      if(String.class.equals(type)){
         return new Converter<String, RequestBody>(){
            @Override
            public RequestBody convert(String value){
               return RequestBody.create(MEDIA_TYPE, value);
            }
         };
      }
      return null;
   }
}
