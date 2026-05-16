package com.debora.inmobiliaria.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.debora.inmobiliaria.R;
import com.debora.inmobiliaria.databinding.FragmentInmueblesBinding;
import com.debora.inmobiliaria.modelo.Inmueble;
import com.debora.inmobiliaria.modelo.Propietario;

import java.util.List;

public class InmueblesFragment extends Fragment {

    private InmueblesViewModel mViewModel;
    private FragmentInmueblesBinding binding;

    public static InmueblesFragment newInstance() {
        return new InmueblesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(InmueblesViewModel.class);
        binding=FragmentInmueblesBinding.inflate(inflater, container, false);
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        binding.rview.setLayoutManager(glm);

        mViewModel.getListaInmuebles().observe(getViewLifecycleOwner(), listainmuebles -> {
            InmuebleAdapter adapter = new InmuebleAdapter(listainmuebles, getLayoutInflater());
            binding.rview.setAdapter(adapter);
        });
        mViewModel.obtenerListaInmuebles();
        return binding.getRoot();
    }


}