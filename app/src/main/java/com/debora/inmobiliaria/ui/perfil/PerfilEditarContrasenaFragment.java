package com.debora.inmobiliaria.ui.perfil;

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
import com.debora.inmobiliaria.databinding.FragmentPerfilBinding;
import com.debora.inmobiliaria.databinding.FragmentPerfilEditarContrasenaBinding;

public class PerfilEditarContrasenaFragment extends Fragment {

    private PerfilEditarContrasenaViewModel mViewModel;
    private FragmentPerfilEditarContrasenaBinding binding;

    public static PerfilEditarContrasenaFragment newInstance() {
        return new PerfilEditarContrasenaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPerfilEditarContrasenaBinding.inflate(inflater, container, false);
        mViewModel= new ViewModelProvider(this).get(PerfilEditarContrasenaViewModel.class);
        View root = binding.getRoot();
        mViewModel.getExito().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        Navigation.findNavController(requireView()).navigate(R.id.nav_perfil);
                    }
        });

        binding.btnCambiar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewModel.cambiarContra(binding.etPassword.getText().toString(), binding.etNuevaPass.getText().toString());

                    }
        });
        return root;
    }


}