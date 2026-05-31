package com.debora.inmobiliaria.ui.contratos;

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
import com.debora.inmobiliaria.databinding.FragmentContratosBinding;


public class ContratosFragment extends Fragment {

    private ContratosViewModel mViewModel;
    private FragmentContratosBinding binding;

    public static ContratosFragment newInstance() {
        return new ContratosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel=new ViewModelProvider(this).get(ContratosViewModel.class);
        binding= FragmentContratosBinding.inflate(inflater, container, false);
        GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        binding.rview.setLayoutManager(glm);
        mViewModel.getListaInmuebles().observe(getViewLifecycleOwner(), listainmuebles -> {
            AlquiladosAdapter adapter = new AlquiladosAdapter(listainmuebles, getLayoutInflater());
            binding.rview.setAdapter(adapter);
        });
        mViewModel.obtenerListaInmuebles();
        return binding.getRoot();
    }


}