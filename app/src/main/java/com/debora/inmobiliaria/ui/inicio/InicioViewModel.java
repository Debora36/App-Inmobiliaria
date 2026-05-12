package com.debora.inmobiliaria.ui.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.debora.inmobiliaria.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioViewModel extends AndroidViewModel {


    //Creamos la clase interna que implementa un metedo de la libreria
    public class MapaActual implements OnMapReadyCallback {

        //Usamos el objeto LatLng para crear las coordenadas
        LatLng ULP = new LatLng(-33.150720, -66.306864);

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            //Creamos los marcadores en el mapa con el metodo addMarker de la clase GoogleMap
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions()
                    .position(ULP)
                    .title("Inmobiliaria")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)))
                    .showInfoWindow();//que se muestre siempre el marcador

            //Eso le da movimiento al mapa
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(ULP)      // Sets the center of the map to Mountain View
                    .zoom(15)             // Sets the zoom
                    .bearing(0)           // Sets the orientation of the camera to east
                    .tilt(30)             // Sets the tilt of the camera to 30 degrees
                    .build();             // Creates a CameraPosition from the builder

            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }
    }

    private MutableLiveData<MapaActual> mapaActualMutable;
    public InicioViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<MapaActual> getMapaActual(){
        if (mapaActualMutable==null){
            mapaActualMutable = new MutableLiveData<>();
        }
        return mapaActualMutable;
    }
    //Metodo par acargar el mapa
    public void cargarMapa(){
        MapaActual nuevoMapaActual = new MapaActual();
        mapaActualMutable.setValue(nuevoMapaActual);
    }
}