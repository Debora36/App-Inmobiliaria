package com.debora.inmobiliaria.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.debora.inmobiliaria.modelo.Inmueble;
import com.debora.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetalleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmuebleMutable;

    public InmuebleDetalleViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Inmueble> getInmuebleMutable() {
        if (inmuebleMutable==null){
            inmuebleMutable=new MutableLiveData<>();
        }
        return inmuebleMutable;
    }
    public void cargarDetalleInmueble(Bundle bundle){
        Inmueble bundleInmueble = bundle.getSerializable("inmueble", Inmueble.class);
        inmuebleMutable.setValue(bundleInmueble);
    }

    public void cambiarDisponibilidad(boolean disponible) {
        Inmueble inmueble = inmuebleMutable.getValue();
        inmueble.setDisponible(disponible);
        String token = ApiClient.usarToken(getApplication());
        ApiClient.ServicioInmobiliaria api = ApiClient.obtenerServicio();

        Call<Inmueble> call = api.cambiarDisponible(token, inmueble);
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    Inmueble inmueble = response.body();
                    inmuebleMutable.postValue(inmueble);
                }else{
                    Log.d("Inmueble", "Código error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.d("Inmueble", "Fallo onFailure: " + t.getMessage());
            }
        });

    }
}
