package com.debora.inmobiliaria.ui.Pagos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.debora.inmobiliaria.R;
import com.debora.inmobiliaria.modelo.Pago;
import com.debora.inmobiliaria.ui.contratos.AlquiladosAdapter;

import java.util.List;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.ViewHolderPagoInmueble>{

    private List<Pago> listaPagos;
    private LayoutInflater inflater;

    public PagosAdapter(List<Pago> listaPagos, LayoutInflater inflater) {
        this.listaPagos = listaPagos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderPagoInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.itempago, parent,false);
        return  new ViewHolderPagoInmueble(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPagoInmueble holder, int position) {
        Pago pagoActual= listaPagos.get(position);
        holder.fecha.setText(pagoActual.getFechaPago());
        holder.monto.setText("$"+pagoActual.getMonto());
        holder.detalle.setText(pagoActual.getDetalle());
        if(pagoActual.getEstado()){//asumo que el estado se trata de si esta pagado o pendiente de pago
            holder.estado.setText("Pendiente");
        }else{
            holder.estado.setText("Pagado");
        }
    }

    @Override
    public int getItemCount() {
        return listaPagos != null ? listaPagos.size() : 0;
    }


    public static class ViewHolderPagoInmueble extends RecyclerView.ViewHolder {
        TextView fecha, monto, detalle, estado;
        public ViewHolderPagoInmueble(@NonNull View itemView) {
            super(itemView);
            fecha=itemView.findViewById(R.id.fechaPago);
            monto=itemView.findViewById(R.id.montoPago);
            detalle=itemView.findViewById(R.id.detallePago);
            estado=itemView.findViewById(R.id.estadoPago);
        }
    }
}
