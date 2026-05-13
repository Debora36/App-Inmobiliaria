package com.debora.inmobiliaria.ui.perfil;

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
import com.debora.inmobiliaria.databinding.ActivityLoginBinding;
import com.debora.inmobiliaria.databinding.FragmentPerfilBinding;
import com.debora.inmobiliaria.modelo.Propietario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel mViewModel;
    private FragmentPerfilBinding binding;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        mViewModel= new ViewModelProvider(this).get(PerfilViewModel.class);
        View root = binding.getRoot();

        mViewModel.getPropietarioM().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etDni.setText(propietario.getDni());
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etCorreo.setText(propietario.getEmail());
                binding.etTelefono.setText(propietario.getTelefono());
            }
        });
        // observer del estado
        mViewModel.getEditableM().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean esEditable) {
                // habilitar o deshabilitar según el mutable
                binding.etDni.setEnabled(esEditable);
                binding.etNombre.setEnabled(esEditable);
                binding.etApellido.setEnabled(esEditable);
                binding.etCorreo.setEnabled(esEditable);
                binding.etTelefono.setEnabled(esEditable);

                // Cambiar el texto del boton
                binding.button.setText(esEditable ? "Guardar" : "Editar");
            }
        });

        // Listener del boton
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.cambiarEstadoEdicion();//Si esta en EDITAR habilita los campos y si esta en GUARDAR, actualiza los campos
            }
        });
        mViewModel.cargarPerfil();
        return root;
    }


}