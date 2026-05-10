package com.debora.inmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.debora.inmobiliaria.modelo.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class ApiClient {
    public final static String BASE_URL = "https://capacitacion.alwaysdata.net/";
    public static ServicioInmobiliaria obtenerServicio(){
        Gson gson = new GsonBuilder().setLenient().create();//Gson personalizado
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
                return retrofit.create(ServicioInmobiliaria.class);//creamos una implementacion automatica del servicio que genera internamente el codigo necesario para hacer las peticiones
    }
    public interface ServicioInmobiliaria{
        //metodos consultas
        //login
        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> login(@Field("Usuario") String usuario, @Field("Clave") String clave);//espero un string y paso dos campos

        //metodo para llamr al propietario
        @GET("api/propietarios")
            Call<Propietario> getPropietario (@Header("Authorization") String token);

    }
    //creamos el token
    public static void crearToken(Context context, String token) {//guardo el token
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", "Bearer "+token);
        editor.apply();
    }
    public static String usarToken(Context context) {//leo el token
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }
}

