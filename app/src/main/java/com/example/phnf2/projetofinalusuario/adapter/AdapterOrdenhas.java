package com.example.phnf2.projetofinalusuario.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phnf2.projetofinalusuario.R;
import com.example.phnf2.projetofinalusuario.modelo.Ordenha;

import java.util.List;

public class AdapterOrdenhas  extends RecyclerView.Adapter {

    private Context context;
    private List<Ordenha> listaOrdenha;
    private Ordenha ordenhaescolhida;


    public AdapterOrdenhas(Context context, List<Ordenha> listaOrdenha) {
        this.context = context;
        this.listaOrdenha = listaOrdenha;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.ordenhas, parent,false);

        ViewHolderOrdenha viewHolderOrdenha = new ViewHolderOrdenha(view);
        return viewHolderOrdenha;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderOrdenha viewHolderOrdenha = (ViewHolderOrdenha) holder;

        ordenhaescolhida = listaOrdenha.get(position);

        viewHolderOrdenha.textGord2.setText(""+ordenhaescolhida.getGord());
        viewHolderOrdenha.textProt2.setText(""+ordenhaescolhida.getProt());
        viewHolderOrdenha.textCas2.setText(""+ordenhaescolhida.getCas());
        viewHolderOrdenha.textLact2.setText(""+ordenhaescolhida.getLact());
        viewHolderOrdenha.textSt2.setText(""+ordenhaescolhida.getSt());
        viewHolderOrdenha.textEsd2.setText(""+ordenhaescolhida.getEsd());
        viewHolderOrdenha.textNu2.setText(""+ordenhaescolhida.getNu());
        viewHolderOrdenha.textCel2.setText(""+ordenhaescolhida.getCel());
        viewHolderOrdenha.textCcs2.setText(""+ordenhaescolhida.getCcs());

    }

    @Override
    public int getItemCount() {
        return listaOrdenha == null ? 0 : listaOrdenha.size();
    }

    public class ViewHolderOrdenha extends RecyclerView.ViewHolder {

        final TextView textGord2;
        final TextView textProt2;
        final TextView textCas2;
        final TextView textLact2;
        final TextView textSt2;
        final TextView textEsd2;
        final TextView textNu2;
        final TextView textCel2;
        final TextView textCcs2;


        public ViewHolderOrdenha(@NonNull View itemView) {
            super(itemView);

            textGord2 =itemView.findViewById(R.id.GordText);
            textProt2 = itemView.findViewById(R.id.ProtText);
            textCas2 = itemView.findViewById(R.id.CasText);
            textLact2 = itemView.findViewById(R.id.LactText);
            textSt2 = itemView.findViewById(R.id.StText);
            textEsd2 = itemView.findViewById(R.id.EsdText);
            textNu2 = itemView.findViewById(R.id.NuText);
            textCel2 = itemView.findViewById(R.id.CelText);
            textCcs2 = itemView.findViewById(R.id.CcsText);

        }
    }


}
