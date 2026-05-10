package com.debora.inmobiliaria;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.debora.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> mensaje;
    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }

    public MutableLiveData<String> getMensaje() {
        if(mensaje==null){
        mensaje=new MutableLiveData<>();}
        return mensaje;
    }


    public void recuperarDatos(String user, String pass){
        if(user.isEmpty() || pass.isEmpty()){
            mensaje.setValue("complete todos los campos");
        }
        else {//implementar interfaz
            ApiClient.ServicioInmobiliaria servicio = ApiClient.obtenerServicio();
            Call<String> call =servicio.login(user, pass);//creo el call que guarda lo que devuelva el metodo login
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    //se ejecuta cuanto todo sale bien
                    if (response.isSuccessful()){//si la respuesta es exitosa creo el token
                        String token = response.body();//recupera el string
                        ApiClient.crearToken(context, token);//lo guardo en el archivo de preferencia
                        Log.d("token", token);
                        irActivityDos();
                    }else{
                        mensaje.setValue("datos incorrectos");
                        Log.d("Error", response.message());//mensaje del erro
                        Log.d("Error", response.code()+"");//codigo del error
                        Log.d("Error", response.errorBody().toString()+"");//error en conjunto con codigo
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    //se ejecuta cuanto tengo un problema
                    Log.d("mensaje",t.getMessage());
                }
            });
        }
    }
    public void irActivityDos() {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
