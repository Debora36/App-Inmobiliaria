package com.debora.inmobiliaria.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.debora.inmobiliaria.modelo.Propietario;
import com.debora.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilEditarContrasenaViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> exito;
    public PerfilEditarContrasenaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getExito() {
        if (exito == null) {
            exito = new MutableLiveData<>();
        }
        return exito;
    }
    public void cambiarContra(String actual, String nueva){
        if(!actual.isEmpty() && !nueva.isEmpty()){
            String token = ApiClient.usarToken(getApplication());
            ApiClient.ServicioInmobiliaria servicio = ApiClient.obtenerServicio();//Objeto que implementa la interfaz
            Call<Void> call = servicio.CambiarContra(token, actual, nueva);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "Contrasenia cambiada", Toast.LENGTH_LONG).show();
                        exito.setValue(true);
                    } else {
                        Toast.makeText(getApplication(), "Se produjo un error", Toast.LENGTH_LONG).show();
                        exito.setValue(false);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error", Toast.LENGTH_LONG).show();
                    exito.setValue(false);
                }
            });
        }else{
            Toast.makeText(getApplication(), "Complete los campos", Toast.LENGTH_LONG).show();
        }
    }
}