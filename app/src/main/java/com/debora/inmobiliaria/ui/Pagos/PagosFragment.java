package com.debora.inmobiliaria.ui.Pagos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.debora.inmobiliaria.R;
import com.debora.inmobiliaria.databinding.FragmentPagosBinding;

public class PagosFragment extends Fragment {

    private PagosViewModel mViewModel;
    private FragmentPagosBinding binding;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentPagosBinding.inflate(inflater, container, false);
        mViewModel= new ViewModelProvider(this).get(PagosViewModel.class);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());// muestra los items uno debajo del otro
        binding.rview.setLayoutManager(lm);
        mViewModel.getListaPagos().observe(getViewLifecycleOwner(), listaPagos->{
            PagosAdapter adapter=new PagosAdapter(listaPagos, getLayoutInflater());
            binding.rview.setAdapter(adapter);
        });
        int idContrato = getArguments().getInt("idContrato");
        mViewModel.obtenerListaPagos(idContrato);
        return binding.getRoot();
    }
}