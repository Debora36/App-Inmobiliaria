package com.debora.inmobiliaria.ui.Pagos;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.debora.inmobiliaria.modelo.Inmueble;
import com.debora.inmobiliaria.modelo.Pago;
import com.debora.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Pago>> listaPagos;

    public PagosViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<Pago>> getListaPagos() {
        if (listaPagos==null){
            listaPagos=new MutableLiveData<>();
        }
        return listaPagos;
    }
    public void obtenerListaPagos(int idContrato){
        String token = ApiClient.usarToken(getApplication());
        ApiClient.ServicioInmobiliaria api = ApiClient.obtenerServicio();

        Call<List<Pago>> call = api.getPagos(token, idContrato);
        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if(response.isSuccessful()){
                    listaPagos.postValue(response.body());
                }else{
                    if(response.code()==401 || response.code()==403){
                        Toast.makeText(getApplication(), "error al cargar los pagos", Toast.LENGTH_LONG).show();
                        ApiClient.crearToken(getApplication(), "");//pisa el token si hubo error
                        System.exit(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Log.d("errorPago", t.getMessage());
                Toast.makeText(getApplication(), "error no se cargaron los pagos", Toast.LENGTH_LONG).show();
            }
        });
    }
}