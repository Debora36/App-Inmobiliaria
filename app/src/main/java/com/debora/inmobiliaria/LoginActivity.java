package com.debora.inmobiliaria;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.debora.inmobiliaria.databinding.ActivityLoginBinding;
import com.debora.inmobiliaria.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        //vm= new ViewModelProvider(this).get(LoginActivityViewModel.class);
        setContentView(binding.getRoot());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(LoginActivityViewModel.class);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=binding.etUsuario.getText().toString();
                String clave=binding.etPassword.getText().toString();
                vm.recuperarDatos(user, clave);
            }
        });
        vm.getMensaje().observe(this, mensaje -> {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        });
        vm.leerUnSensor();

        vm.getAgite().observe(this, agito -> {
            llamar(this);
        });
    }
    public void llamar(Context ctx){
        Intent intentLlamada = new Intent(Intent.ACTION_CALL);
        intentLlamada.setData(Uri.parse("tel:2664553747"));
        intentLlamada.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intentLlamada);
    }
}