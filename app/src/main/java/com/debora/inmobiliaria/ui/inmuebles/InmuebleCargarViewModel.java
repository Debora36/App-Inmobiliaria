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
            if(!dire.isEmpty() && !tipo.isEmpty() && !ambientes.isEmpty() && !superficie.isEmpty() && !uso.isEmpty() && !precio.isEmpty()){
                if (tipo.equals("Tipo")) {//Si no se selecciono nada se guardo lo del placeholder
                    Toast.makeText(getApplication(), "Seleccioná el tipo de inmueble", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (uso.equals("Uso")) {
                    Toast.makeText(getApplication(), "Seleccioná el uso del inmueble", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Double.parseDouble(precio) <= 0) {
                    Toast.makeText(getApplication(), "El precio debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(superficie) <= 0) {
                    Toast.makeText(getApplication(), "La superficie debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                    return;
                }
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
                            Toast.makeText(getApplication(), "Inmueble cargado", Toast.LENGTH_LONG).show();
                            exito.postValue(true);
                        }else{
                            Toast.makeText(getApplication(), "ERROR CARGA INMUEBLE", Toast.LENGTH_LONG).show();
                            exito.postValue(false);
                        }
                    }


                    @Override
                    public void onFailure(Call<Inmueble> call, Throwable t) {
                        Log.d("errorCargaInmueble", t.getMessage());
                        Toast.makeText(getApplication(), "error no se cargo el inmueble", Toast.LENGTH_LONG).show();
                        exito.postValue(false);
                    }
                });
            } else {
                Toast.makeText(getApplication(), "Completá todos los campos", Toast.LENGTH_SHORT).show();
            }

        }catch(NumberFormatException e){
            Log.d("cargarInmueble", e+"no se cargo");
        }

    }
    private byte[] transformarImagen() {
        try {
            Uri uri = mutableUri.getValue();
            if (uri == null) {
                Toast.makeText(getApplication(), "Debe ingresar una foto", Toast.LENGTH_LONG).show();
                return new byte[]{};
            }
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