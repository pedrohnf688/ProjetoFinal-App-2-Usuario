package com.example.phnf2.projetofinalusuario.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phnf2.projetofinalusuario.R;
import com.example.phnf2.projetofinalusuario.modelo.RelatorioProducaoLeite;

import java.util.List;

public class AdapterRelatorio extends RecyclerView.Adapter {

    private Context context;
    private List<RelatorioProducaoLeite> listaRelatorio;
    private RelatorioProducaoLeite relatorioescolhido;

    public AdapterRelatorio(Context context, List<RelatorioProducaoLeite> listaRelatorio) {
        this.context = context;
        this.listaRelatorio = listaRelatorio;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.relatorios,viewGroup,false);
        ViewHolderRelatorio holderRelatorio = new ViewHolderRelatorio(view);
        return holderRelatorio;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        ViewHolderRelatorio holderRelatorio = (ViewHolderRelatorio) viewHolder;

        relatorioescolhido = listaRelatorio.get(position);

        holderRelatorio.textTitulo2.setText(relatorioescolhido.getTituloRelatorio());
        holderRelatorio.textTipo2.setText(relatorioescolhido.getTipoRelatorio());
        holderRelatorio.textData2.setText(relatorioescolhido.getDataRelatorio());

    }

    @Override
    public int getItemCount() {
        return listaRelatorio == null ? 0 : listaRelatorio.size();
    }


    public class ViewHolderRelatorio extends RecyclerView.ViewHolder{

        final TextView textTitulo2;
        final TextView textTipo2;
        final TextView textData2;


        public ViewHolderRelatorio(@NonNull View itemView) {
            super(itemView);

            textTitulo2 = itemView.findViewById(R.id.TituloText);
            textTipo2 = itemView.findViewById(R.id.TipoText);
            textData2 = itemView.findViewById(R.id.DataText);


        }
    }
}
