package com.debora.inmobiliaria;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.debora.inmobiliaria.modelo.Propietario;
import com.debora.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> usuarioM = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Propietario> getUsuarioM() {
        return usuarioM;
    }

    public void obtenerUsuarioLogueado() {
        String token = ApiClient.usarToken(getApplication());
        ApiClient.ServicioInmobiliaria servicio = ApiClient.obtenerServicio();
        Call<Propietario> call = servicio.getPropietario(token);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usuarioM.postValue(response.body());
                } else {
                    Log.d("ErrorMainViewModel", "Error de la api" + response.code());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("ErrorMainViewModel", "error conexion " + t.getMessage());
            }
        });
    }
}
