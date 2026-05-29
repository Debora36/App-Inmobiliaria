package com.debora.inmobiliaria.ui.inquilinos;

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
import com.debora.inmobiliaria.databinding.FragmentInquilinosBinding;
import com.debora.inmobiliaria.ui.inmuebles.InmuebleAdapter;

public class InquilinosFragment extends Fragment {

    private InquilinosViewModel mViewModel;
    private FragmentInquilinosBinding binding;

    public static InquilinosFragment newInstance() {
        return new InquilinosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel=new ViewModelProvider(this).get(InquilinosViewModel.class);
        binding=FragmentInquilinosBinding.inflate(inflater,container,false);
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        //binding.rview.setLayoutManager(glm);
        mViewModel.getInmueblesAlquilados().observe(getViewLifecycleOwner(), listainmuebles -> {
            InmuebleAdapter adapter = new InmuebleAdapter(listainmuebles, getLayoutInflater());
            //binding.rview.setAdapter(adapter);
        });
        mViewModel.obtenerInmueblesAlquilados();
        return binding.getRoot();
    }


}