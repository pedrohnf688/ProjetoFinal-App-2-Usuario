package com.example.phnf2.projetofinalusuario.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phnf2.projetofinalusuario.R;
import com.example.phnf2.projetofinalusuario.fragment.Fragment_Inicio;
import com.example.phnf2.projetofinalusuario.modelo.Usuario;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUsers extends RecyclerView.Adapter {

    private Context context;
    private List<Usuario> listaUsuarios;
    Usuario usuarioescolhido;


    public AdapterUsers(Context context, List<Usuario> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.users, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

        }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder listT = (ViewHolder) holder;

        usuarioescolhido = listaUsuarios.get(position);
        boolean fotoX = usuarioescolhido.getPhotoUser() != null;

        listT.textViewNome.setText(usuarioescolhido.getNomeUser());
        listT.textViewEmail.setText(usuarioescolhido.getEmailUser());

        if(fotoX){
            Fragment_Inicio.loadProfileIcon(usuarioescolhido.getPhotoUser(),listT.circleImageUser);
        }else{
            listT.circleImageUser.setImageResource(R.mipmap.iconeprincipal);
        }



     }

    @Override
    public int getItemCount() {
        return listaUsuarios == null ? 0 : listaUsuarios.size();
    }


  public class ViewHolder extends RecyclerView.ViewHolder {

     final TextView textViewNome;
     final TextView textViewEmail;
     final CircleImageView circleImageUser;

    public ViewHolder(View v) {
        super(v);

        textViewNome = v.findViewById(R.id.NomeUsuario);
        textViewEmail = v.findViewById(R.id.EmailUsuario);
        circleImageUser = v.findViewById(R.id.fotoUserListar);
    }
  }

}
