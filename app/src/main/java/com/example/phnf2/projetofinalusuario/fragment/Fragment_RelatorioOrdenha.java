package com.example.phnf2.projetofinalusuario.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
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
import com.example.phnf2.projetofinalusuario.adapter.AdapterOrdenhas;
import com.example.phnf2.projetofinalusuario.adapter.RecyclerOrdenhasClickListener;
import com.example.phnf2.projetofinalusuario.modelo.Ordenha;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Fragment_RelatorioOrdenha extends Fragment {

    String id;
    List<Ordenha> listaOrdenha = new ArrayList<>();
    RecyclerView recyclerViewOrdenha;
    DatabaseReference databaseOrdenha;
    private FirebaseDatabase mFirebase;


    @SuppressLint("ValidFragment")
    public Fragment_RelatorioOrdenha(String id) {
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__relatorio_ordenha, container, false);


        recyclerViewOrdenha = view.findViewById(R.id.recyclerViewOrdenha);


        mFirebase = FirebaseDatabase.getInstance();

        if (mFirebase == null) {

            mFirebase.setPersistenceEnabled(true);
            databaseOrdenha = mFirebase.getReference("Ordenhas").child(id);

        } else {
            databaseOrdenha = mFirebase.getReference("Ordenhas").child(id);
            databaseOrdenha.keepSynced(true);
        }


        recyclerViewOrdenha.addOnItemTouchListener(new RecyclerOrdenhasClickListener(getContext(), recyclerViewOrdenha, new RecyclerOrdenhasClickListener.OnOrdenhaClickListener() {
            @Override
            public void onOrdenhaClick(View view, int position) {

                Ordenha ordenha = listaOrdenha.get(position);

                Fragment_GraficoOrdenha graficoOrdenha = new Fragment_GraficoOrdenha(id,ordenha.getIdOrdenha());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPrincipal, graficoOrdenha).addToBackStack(null).commit();

            }

            @Override
            public void onOrdenhaLongClick(View view, int position) {

                //Testes

            }
        }));


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        databaseOrdenha.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listaOrdenha.clear();

                for (DataSnapshot dataSnapshotOrdenha : dataSnapshot.getChildren()) {

                    Ordenha ordenha = dataSnapshotOrdenha.getValue(Ordenha.class);
                    listaOrdenha.add(ordenha);

                }

                recyclerViewOrdenha.setAdapter(new AdapterOrdenhas(getContext(), listaOrdenha));

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerViewOrdenha.setLayoutManager(layoutManager);
                recyclerViewOrdenha.setItemAnimator(new DefaultItemAnimator());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}