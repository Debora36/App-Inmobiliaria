package com.debora.inmobiliaria.ui.inicio;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.debora.inmobiliaria.R;
import com.debora.inmobiliaria.databinding.FragmentInicioBinding;
import com.debora.inmobiliaria.databinding.FragmentPerfilBinding;
import com.debora.inmobiliaria.ui.perfil.PerfilViewModel;
import com.google.android.gms.maps.SupportMapFragment;

public class InicioFragment extends Fragment {

    private InicioViewModel mViewModel;
    private FragmentInicioBinding binding;

    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        mViewModel= new ViewModelProvider(this).get(InicioViewModel.class);
        View root = binding.getRoot();

        mViewModel.getMapaActual().observe(getViewLifecycleOwner(), new Observer<InicioViewModel.MapaActual>() {
            @Override
            public void onChanged(InicioViewModel.MapaActual mapaActual) {
                ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(mapaActual);// uso getChildFragmentManager() para manejar fragmentos anidados dentro de otro Fragment
            }
        });
        mViewModel.cargarMapa();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InicioViewModel.class);
        // TODO: Use the ViewModel
    }

}