package com.debora.inmobiliaria.ui.inmuebles;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.debora.inmobiliaria.modelo.Inmueble;
import com.debora.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {
    //private MutableLiveData<Inmueble> mInmueble;
    private MutableLiveData<List<Inmueble>> listaInmuebles;

    public LiveData<List<Inmueble>> getListaInmuebles() {
        if (listaInmuebles==null){
            listaInmuebles=new MutableLiveData<>();
        }
        return listaInmuebles;
    }

    public InmueblesViewModel(@NonNull Application application) {

        super(application);
    }
    public void obtenerListaInmuebles(){
        String token = ApiClient.usarToken(getApplication());
        ApiClient.ServicioInmobiliaria api = ApiClient.obtenerServicio();

        Call<List<Inmueble>> call = api.getInmuebles(token);
        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    listaInmuebles.postValue(response.body());
                }else{
                    if(response.code()==401 || response.code()==403){
                        Toast.makeText(getApplication(), "no se cargaron los inmuebles", Toast.LENGTH_LONG).show();
                        ApiClient.crearToken(getApplication(), "");//pisa el token si hubo error
                        System.exit(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.d("errorInmueble", t.getMessage());
                Toast.makeText(getApplication(), "error no se cargaron los inmuebles", Toast.LENGTH_LONG).show();
            }
        });
    }
}