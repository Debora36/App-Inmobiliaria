package com.debora.inmobiliaria.ui.inquilinos;

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
import com.debora.inmobiliaria.databinding.FragmentInquilinoDetalleBinding;
import com.debora.inmobiliaria.modelo.Inquilino;

public class InquilinoDetalleFragment extends Fragment {

    private InquilinoDetalleViewModel mViewModel;
    private FragmentInquilinoDetalleBinding binding;

    public static InquilinoDetalleFragment newInstance() {
        return new InquilinoDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel=new ViewModelProvider(this).get(InquilinoDetalleViewModel.class);
        binding=FragmentInquilinoDetalleBinding.inflate(inflater, container,false);
        mViewModel.getInmuebleMutable().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.tvDNI.setText(inquilino.getDni());
                binding.tvNOMBRE.setText((inquilino.getNombre()));
                binding.tvAPELLIDO.setText(inquilino.getApellido());
                binding.tvCORREO.setText(inquilino.getEmail());
                binding.tvTELEFONO.setText(inquilino.getTelefono());
            }
        });
        int idInmueble = getArguments().getInt("idInmueble");
        mViewModel.cargarDetalleInquilino(idInmueble);

        return binding.getRoot();
    }
}