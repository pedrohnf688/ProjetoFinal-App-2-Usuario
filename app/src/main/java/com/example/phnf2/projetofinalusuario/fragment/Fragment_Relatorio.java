package com.example.phnf2.projetofinalusuario.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phnf2.projetofinalusuario.R;
import com.example.phnf2.projetofinalusuario.adapter.AdapterUsers;
import com.example.phnf2.projetofinalusuario.adapter.RecyclerUsuarioClickListener;
import com.example.phnf2.projetofinalusuario.modelo.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Relatorio extends Fragment {

    public static List<Usuario> listUsuarios = new ArrayList<>();
    RecyclerView recyclerViewUsuario;
    DatabaseReference databaseUsuario;
    private FirebaseDatabase mFirebase;

    public Fragment_Relatorio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__relatorio, container, false);

        recyclerViewUsuario = view.findViewById(R.id.recyclerViewListarUsuarios);


        mFirebase = FirebaseDatabase.getInstance();

        if(mFirebase == null) {

            mFirebase.setPersistenceEnabled(true);
            databaseUsuario = mFirebase.getReference("Usuarios");

        }else{

            databaseUsuario = mFirebase.getReference("Usuarios");
            databaseUsuario.keepSynced(true);
        }




        recyclerViewUsuario.addOnItemTouchListener(new RecyclerUsuarioClickListener(getContext(), recyclerViewUsuario, new RecyclerUsuarioClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Usuario usuario = listUsuarios.get(position);

                Fragment_RelatorioListar fragment_relatorioListar = new Fragment_RelatorioListar(usuario.getIdUser());

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPrincipal,fragment_relatorioListar).addToBackStack(null).commit();

            }

            @Override
            public void onItemLongClick(View view, int position) {

                /*
                Testes
                 */
            }
        }));





        return view;
    }


    @Override
    public void onStart() {
        super.onStart();


        databaseUsuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listUsuarios.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Usuario user3 = dataSnapshot1.getValue(Usuario.class);

                    listUsuarios.add(user3);

                }

                AdapterUsers adapter = new AdapterUsers(getContext(), listUsuarios);
                recyclerViewUsuario.setAdapter(adapter);

                RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerViewUsuario.setLayoutManager(layout);
                recyclerViewUsuario.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }





}
