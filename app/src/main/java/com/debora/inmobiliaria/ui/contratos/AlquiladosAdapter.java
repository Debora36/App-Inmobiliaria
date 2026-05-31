package com.debora.inmobiliaria.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.debora.inmobiliaria.R;
import com.debora.inmobiliaria.modelo.Inmueble;
import com.debora.inmobiliaria.request.ApiClient;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class AlquiladosAdapter extends RecyclerView.Adapter<AlquiladosAdapter.ViewHolderInmuebleAlquilado>{
    private List<Inmueble> listaInmuebles;
    private LayoutInflater inflater;
    public AlquiladosAdapter(List<Inmueble> inmuebles, LayoutInflater inflater) {
        this.listaInmuebles = inmuebles;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderInmuebleAlquilado onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.itemalquilado, parent, false);
        return new ViewHolderInmuebleAlquilado(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmuebleAlquilado holder, int position) {
        Inmueble inmuebleActual = listaInmuebles.get(position);
        holder.direccion.setText(inmuebleActual.getDireccion());
        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "AR"));
        String valorFormateado = nf.format(inmuebleActual.getValor());
        holder.precio.setText("$ " + valorFormateado);
        Glide.with(holder.itemView.getContext())
                .load(ApiClient.BASE_URL + inmuebleActual.getImagen())
                .placeholder(R.drawable.loading)
                .error(R.drawable.house)
                .transform(new CenterCrop(),
                        new GranularRoundedCorners(20, 20, 5, 5))
                .into(holder.foto);//donde se infla

        Bundle b = new Bundle();
        b.putInt("idInmueble", inmuebleActual.getIdInmueble());
        holder.btnContrato.setOnClickListener(v -> {
            Navigation.findNavController(v)
                    .navigate(R.id.action_nav_contratos_to_contratoDetalleFragment22, b);
        });

        holder.btnInquilino.setOnClickListener(v -> {
            Navigation.findNavController(v)
                    .navigate(R.id.action_nav_contratos_to_inquilinoDetalleFragment, b);
        });

    }

    @Override
    public int getItemCount() {
        return listaInmuebles != null ? listaInmuebles.size() : 0;
    }

    public static class ViewHolderInmuebleAlquilado extends RecyclerView.ViewHolder {
        TextView direccion, precio;
        ImageView foto;
        Button btnInquilino, btnContrato;

        public ViewHolderInmuebleAlquilado(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            precio = itemView.findViewById(R.id.tvPrecio);
            foto = itemView.findViewById(R.id.imagenmueble);
            btnInquilino= itemView.findViewById(R.id.btnInquilino);
            btnContrato=itemView.findViewById(R.id.btnContrato);
        }
    }
}
