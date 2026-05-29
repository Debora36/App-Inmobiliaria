package com.debora.inmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.debora.inmobiliaria.R;
import com.debora.inmobiliaria.databinding.FragmentContratoDetalleBinding;
import com.debora.inmobiliaria.databinding.FragmentContratosBinding;
import com.debora.inmobiliaria.modelo.Contrato;

public class ContratoDetalleFragment extends Fragment {

    private ContratoDetalleViewModel mViewModel;
    private FragmentContratoDetalleBinding binding;

    public static ContratoDetalleFragment newInstance() {
        return new ContratoDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       mViewModel=new ViewModelProvider(this).get(ContratoDetalleViewModel.class);
       binding=FragmentContratoDetalleBinding.inflate(inflater,container, false);
      mViewModel.getContratoMutable().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
          @Override
          public void onChanged(Contrato contrato) {
              binding.direInmueble.setText(contrato.getInmueble().getDireccion());
              binding.fechaInicio.setText(contrato.getFechaInicio());
              binding.fechaF.setText(contrato.getFechaFinalizacion());
              binding.inquilino.setText(contrato.getInquilino().getNombre());
              binding.estado.setText(contrato.getEstado().toString());
              binding.monto.setText("$"+contrato.getMontoAlquiler());
          }
      });
        int idInmueble = getArguments().getInt("idInmueble");
        mViewModel.cargarContrato(idInmueble);

        binding.btnPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(v);
                nav.navigate(R.id.action_contratoDetalleFragment2_to_pagosFragment);
            }
        });
       return binding.getRoot();

    }
}