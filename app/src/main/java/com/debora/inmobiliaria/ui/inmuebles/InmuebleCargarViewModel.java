package com.debora.inmobiliaria.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.debora.inmobiliaria.modelo.Inmueble;
import com.debora.inmobiliaria.modelo.Propietario;
import com.debora.inmobiliaria.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleCargarViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> mutableUri;
    private  MutableLiveData<Boolean> exito;
    public InmuebleCargarViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Uri> getMutableUri(){
        if(mutableUri==null){
            mutableUri=new MutableLiveData<>();
        }
        return mutableUri;
    }

    public LiveData<Boolean> getExito() {
        if (exito == null) {
            exito = new MutableLiveData<>();
        }
        return exito;
    }
    public void recibirFoto(ActivityResult resultado) {
        if (resultado.getResultCode() == RESULT_OK) {
            Intent data = resultado.getData();
            Uri uri = data.getData();
            Log.d("salada", uri.toString());
            mutableUri.setValue(uri);
        }
    }
    public void cargarInmueble(String dire, String tipo, String ambientes, String superficie, String uso, String precio, Boolean disponible){

        try{
            if(!dire.isEmpty()||!tipo.isEmpty()||!ambientes.isEmpty()||!superficie.isEmpty()||!uso.isEmpty()||!precio.isEmpty()){
                Inmueble inmueble= new Inmueble();
                inmueble.setDireccion(dire);
                inmueble.setTipo(tipo);
                inmueble.setAmbientes(Integer.parseInt(ambientes));
                inmueble.setSuperficie(Integer.parseInt(superficie));
                inmueble.setUso(uso);
                inmueble.setValor(Double.parseDouble(precio));
                inmueble.setDisponible(disponible);
                //agrego la imagen
                byte[] imagen = transformarImagen();
                if (imagen.length==0){
                    Toast.makeText(getApplication(), "Debe ingresar una foto", Toast.LENGTH_LONG).show();
                    return;
                }
                String inmuebleJson = new Gson().toJson(inmueble);//inmueble trasnformado en json
                RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
                MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);//prepara la imagen como un adjunto para mandarla al servidor. imagen es el nombre del campo en la api

                String token = ApiClient.usarToken(getApplication());
                ApiClient.ServicioInmobiliaria api = ApiClient.obtenerServicio();

                Call<Inmueble> call = api.cargarInmueble(token, imagenPart,inmuebleBody);
                call.enqueue(new Callback<Inmueble>() {
                    @Override
                    public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplication(), "inmueble cargado", Toast.LENGTH_LONG).show();
                            exito.setValue(true);
                        }else{
                            Toast.makeText(getApplication(), "ERROR CARGA INMUEBLE", Toast.LENGTH_LONG).show();
                            exito.setValue(false);
                        }
                    }


                    @Override
                    public void onFailure(Call<Inmueble> call, Throwable t) {
                        Log.d("errorCargaInmueble", t.getMessage());
                        Toast.makeText(getApplication(), "error no se cargo el inmueble", Toast.LENGTH_LONG).show();
                        exito.setValue(false);
                    }
                });
            }

        }catch(NumberFormatException e){
            Log.d("cargarInmueble", e+"no se cargo");
        }

    }
    private byte[] transformarImagen() {
        try {
            Uri uri = mutableUri.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException ex) {
            Toast.makeText(getApplication(), "Debe ingresar una foto", Toast.LENGTH_LONG).show();
            return new byte[]{};
        }

    }

}