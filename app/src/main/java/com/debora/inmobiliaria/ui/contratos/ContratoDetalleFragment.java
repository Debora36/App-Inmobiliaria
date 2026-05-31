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
              binding.estado.setText(contrato.getEstadoTexto());//uso este metodo para que en vez de true o false me muetre algo mas descriptivo
              binding.monto.setText("$"+contrato.getMontoAlquiler());
          }
      });
        int idInmueble = getArguments().getInt("idInmueble");
        mViewModel.cargarContrato(idInmueble);

        binding.btnPagos.setOnClickListener(v -> mViewModel.verPagos());

        mViewModel.getIdContratoMutable().observe(getViewLifecycleOwner(), idContrato -> {
            if (idContrato == null) return;//Utilizo el if para asegurar que la vista solo se ejecute cuando hago click en el boton y se asigna el ID real y no cuando hago click en la flecha desde pagos a contrato detalle
            Bundle b = new Bundle();
            b.putInt("idContrato", idContrato);
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_contratoDetalleFragment2_to_pagosFragment, b);
        });

       return binding.getRoot();

    }
}