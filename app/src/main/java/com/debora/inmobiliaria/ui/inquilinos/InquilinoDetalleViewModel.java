package com.debora.inmobiliaria.ui.inquilinos;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.debora.inmobiliaria.modelo.Contrato;
import com.debora.inmobiliaria.modelo.Inmueble;
import com.debora.inmobiliaria.modelo.Inquilino;
import com.debora.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class InquilinoDetalleViewModel extends AndroidViewModel {
    private MutableLiveData<Inquilino> inquilinoMutable;

    public InquilinoDetalleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inquilino> getInmuebleMutable() {
        if (inquilinoMutable==null){
            inquilinoMutable=new MutableLiveData<>();
        }
        return inquilinoMutable;
    }
    public void cargarDetalleInquilino(int idInmueble){
        Log.d("Inquilino", "idInmueble recibido: " + idInmueble);
        String token = ApiClient.usarToken(getApplication());
        ApiClient.ServicioInmobiliaria api = ApiClient.obtenerServicio();

        Call<Contrato> call = api.getContrato(token, idInmueble);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if(response.isSuccessful()){
                    Inquilino inquilino = response.body().getInquilino();
                    inquilinoMutable.postValue(inquilino);
                }else{
                    Log.d("Inquilino", "codigo error: " + response.code());
                    Toast.makeText(getApplication(), "Se produjo un error", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.d("Inquilino", "fallo: " + t.getMessage());
                Toast.makeText(getApplication(), "Se produjo un error", Toast.LENGTH_LONG).show();
            }
        });
    }

}