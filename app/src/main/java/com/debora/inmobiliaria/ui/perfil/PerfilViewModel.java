package com.debora.inmobiliaria.ui.perfil;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Api26Impl;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.debora.inmobiliaria.modelo.Propietario;
import com.debora.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> propietarioM;
    private MutableLiveData<Boolean> editableM;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Propietario> getPropietarioM() {
        if(propietarioM==null){
            propietarioM=new MutableLiveData<>();
        }
        return propietarioM;
    }
    public LiveData<Boolean> getEditableM() {
        if (editableM == null) {
            editableM = new MutableLiveData<>();
            editableM.setValue(false); // arranca con los campos bloqueados
        }
        return editableM;
    }

    public void cargarPerfil(){
        editableM.setValue(false);
        String token=ApiClient.usarToken(getApplication());
        ApiClient.ServicioInmobiliaria servicio = ApiClient.obtenerServicio();
        Call<Propietario> call = servicio.getPropietario(token);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()){
                    Propietario p = response.body();
                    propietarioM.postValue(p);
                    editableM.postValue(false);
                }else{
                    Log.d("Perfil", "Código error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("Perfil", "Fallo: " + t.getMessage());
            }
        });

    }

    public void cambiarEstadoEdicion() {
        // Si era true pasa a false y viceversa
        boolean nuevoEstado = !editableM.getValue();
        editableM.setValue(nuevoEstado);

        if (!nuevoEstado) {
            //actualizar datos
        }
    }

}