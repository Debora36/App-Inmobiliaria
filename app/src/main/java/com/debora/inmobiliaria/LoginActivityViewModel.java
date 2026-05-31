package com.debora.inmobiliaria;

import static android.content.Context.SENSOR_SERVICE;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.debora.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Boolean> agite;
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

    public LiveData<Boolean> getAgite() {
        if (agite == null) {
            agite = new MutableLiveData<>();
        }
        return agite;
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

    public void leerUnSensor(){
        SensorManager sm = (SensorManager) getApplication().getSystemService(SENSOR_SERVICE);
        List<Sensor> sensores = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);

        if (sensores.size()!=0){
            Sensor acelerometro = sensores.get(0);
            sm.registerListener(new ManejaEventos(),acelerometro,SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    private class ManejaEventos implements SensorEventListener {
        private long ultimoAgite;

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            double fuerza = Math.sqrt(x * x + y * y + z * z);
            if (fuerza > 15) {
                long ahora = System.currentTimeMillis();
                if (ahora - ultimoAgite > 2000) {
                    ultimoAgite = ahora;
                    agite.postValue(true);
                }
            }
        }
    }
}
