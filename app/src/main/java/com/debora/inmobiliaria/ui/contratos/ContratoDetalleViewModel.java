package com.debora.inmobiliaria.ui.contratos;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.debora.inmobiliaria.modelo.Contrato;
import com.debora.inmobiliaria.modelo.Inmueble;
import com.debora.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoDetalleViewModel extends AndroidViewModel{
private MutableLiveData<Contrato> contratoMutable;
    public ContratoDetalleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Contrato> getContratoMutable(){
        if(contratoMutable==null){
            contratoMutable=new MutableLiveData<>();
        }
        return contratoMutable;
    }
    public void cargarContrato(int idInmueble) {
        String token = ApiClient.usarToken(getApplication());
        ApiClient.ServicioInmobiliaria api = ApiClient.obtenerServicio();

        Call<Contrato> call = api.getContrato(token, idInmueble);
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if(response.isSuccessful()){
                    Contrato contrato= response.body();
                    contratoMutable.postValue(contrato);
                }else{
                    Log.d("Contrato", "codigo error "+response.code());
                    Toast.makeText(getApplication(), "Se produjo un error al cargar el contrato", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                    Log.d("Contrato", "Fallo onFailure "+t.getMessage());
                Toast.makeText(getApplication(), "Se produjo un error onFailure", Toast.LENGTH_LONG).show();
            }
        });

    }

}