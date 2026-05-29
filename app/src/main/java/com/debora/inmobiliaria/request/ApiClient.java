package com.debora.inmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.debora.inmobiliaria.modelo.Contrato;
import com.debora.inmobiliaria.modelo.Inmueble;
import com.debora.inmobiliaria.modelo.Pago;
import com.debora.inmobiliaria.modelo.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

        @PUT("api/Propietarios/actualizar")
            Call<Propietario> actualizarPerfil(@Header("Authorization")String token, @Body Propietario propietario);

        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
            Call<Void>CambiarContra(@Header("Authorization")String token, @Field("CurrentPassword")String actual, @Field("newPassword")String nueva);

        @GET("/api/Inmuebles")
        Call<List<Inmueble>> getInmuebles(@Header("Authorization") String token);


        @PUT("api/Inmuebles/actualizar")
        Call<Inmueble>cambiarDisponible(@Header("Authorization") String token, @Body Inmueble inmueble);

        @Multipart//SE USA PARA ENVIAR ARCHIVO
        @POST("api/Inmuebles/cargar")
        Call<Inmueble> cargarInmueble(@Header("Authorization") String token,
                                      @Part MultipartBody.Part imagen,
                                      @Part("inmueble") RequestBody inmuebleBody);//RequestBody envia el inmueble en formato json (string)

        @GET("api/Inmuebles/GetContratoVigente")
        Call<List<Inmueble>> getInmueblesAlquilados(@Header("Authorization") String token);

        @GET("api/contratos/inmueble/{id}")
        Call<Contrato> getContrato(@Header("Authorization") String token, @Path("id") int id);

        @GET("api/pagos/contrato/{id}")
        Call<List<Pago>> getPagos(@Header("Authorization") String token, @Path("id")int id);



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

