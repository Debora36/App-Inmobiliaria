package com.debora.inmobiliaria.ui.inmuebles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.debora.inmobiliaria.R;
import com.debora.inmobiliaria.databinding.FragmentInmuebleDetalleBinding;
import com.debora.inmobiliaria.databinding.FragmentInmueblesBinding;
import com.debora.inmobiliaria.modelo.Inmueble;
import com.debora.inmobiliaria.modelo.Propietario;
import com.debora.inmobiliaria.request.ApiClient;

public class InmuebleDetalleFragment extends Fragment {

    private InmuebleDetalleViewModel mViewModel;
    private FragmentInmuebleDetalleBinding binding;
    public static InmueblesFragment newInstance() {
        return new InmueblesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(InmuebleDetalleViewModel.class);
        binding=FragmentInmuebleDetalleBinding.inflate(inflater, container, false);
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        mViewModel.getInmuebleMutable().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                Glide.with(getContext())
                        .load(ApiClient.BASE_URL + inmueble.getImagen())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.house)
                        .into(binding.imagenInmueble);
                binding.direccion.setText(inmueble.getDireccion());
                binding.ambientes.setText(inmueble.getAmbientes()+"");
                binding.tipo.setText(inmueble.getTipo());
                binding.uso.setText(inmueble.getUso());
                binding.superficie.setText(inmueble.getSuperficie()+"");
                binding.precio.setText("$"+inmueble.getValor());
                binding.disponible.setChecked(inmueble.isDisponible());

            }
        });
        binding.disponible.setOnClickListener(v->{
            mViewModel.cambiarDisponibilidad(binding.disponible.isChecked());
        });
        //valor del bundle recibido a traves de getArgumento
        mViewModel.cargarDetalleInmueble(getArguments());
        return binding.getRoot();
    }
}