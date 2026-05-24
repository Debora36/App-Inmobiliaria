package com.debora.inmobiliaria.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.debora.inmobiliaria.R;
import com.debora.inmobiliaria.databinding.FragmentInmuebleCargarBinding;
import com.debora.inmobiliaria.databinding.FragmentInmueblesBinding;

public class InmuebleCargarFragment extends Fragment {

    private InmuebleCargarViewModel mViewModel;
    private FragmentInmuebleCargarBinding binding;
    private ActivityResultLauncher<Intent> selector;
    private Intent intent;
    public static InmuebleCargarFragment newInstance() {
        return new InmuebleCargarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding=FragmentInmuebleCargarBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(InmuebleCargarViewModel.class);

        mViewModel.getMutableUri().observe(getViewLifecycleOwner(), uri->{
            binding.imagenInmueble.setImageURI(uri);
        });
        binding.btnBuscarImagen.setOnClickListener(view->{
           selector.launch(intent);
        });
        binding.btnCargarInmueble.setOnClickListener(view->{
            mViewModel.cargarInmueble(
                    binding.etDireccion.getText().toString(),
                    binding.etTipo.getSelectedItem().toString(),
                    binding.etAmbientes.getText().toString(),
                    binding.etSuperficie.getText().toString(),
                    binding.etUso.getSelectedItem().toString(),
                    binding.etPrecio.getText().toString(),
                    binding.disponible.isChecked()
            );
        });
        mViewModel.getExito().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Navigation.findNavController(requireView()).navigate(R.id.nav_inmuebles);}
            }
        });
        abrirGaleria();
        return binding.getRoot();
    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selector = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult resultado) {
                mViewModel.recibirFoto(resultado);
            }

        });

    }

}