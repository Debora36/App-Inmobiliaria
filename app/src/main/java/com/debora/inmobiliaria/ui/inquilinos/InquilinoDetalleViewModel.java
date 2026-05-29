package com.debora.inmobiliaria.ui.inquilinos;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.debora.inmobiliaria.modelo.Inmueble;
import com.debora.inmobiliaria.modelo.Inquilino;

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
    public void cargarDetalleInquilino(Bundle bundle){
        Inquilino bundleInquilino = bundle.getSerializable("inquilino", Inquilino.class);
        inquilinoMutable.setValue(bundleInquilino);
    }

}