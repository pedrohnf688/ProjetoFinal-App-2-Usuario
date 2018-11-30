package com.example.phnf2.projetofinalusuario.fragment;


import android.annotation.SuppressLint;
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
import com.example.phnf2.projetofinalusuario.adapter.AdapterRelatorio;
import com.example.phnf2.projetofinalusuario.adapter.RecyclerRelatorioClickListener;
import com.example.phnf2.projetofinalusuario.modelo.RelatorioProducaoLeite;
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
@SuppressLint("ValidFragment")
public class Fragment_RelatorioListar extends Fragment {

    String id;
    List<RelatorioProducaoLeite> listarRelatorios = new ArrayList<>();
    RecyclerView recyclerViewRelatorio;
    DatabaseReference databaseRelatorio;
    private FirebaseDatabase mFirebase;



    @SuppressLint("ValidFragment")
    public Fragment_RelatorioListar(String id) {
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__relatorio_listar, container, false);

        recyclerViewRelatorio =view.findViewById(R.id.recyclerViewRelatorios);

        mFirebase = FirebaseDatabase.getInstance();

        if(mFirebase == null) {

            mFirebase.setPersistenceEnabled(true);
            databaseRelatorio = mFirebase.getReference("Relatorios").child(id);

        }else{
            databaseRelatorio = mFirebase.getReference("Relatorios").child(id);
            databaseRelatorio.keepSynced(true);
        }




        recyclerViewRelatorio.addOnItemTouchListener(new RecyclerRelatorioClickListener(getContext(), recyclerViewRelatorio, new RecyclerRelatorioClickListener.OnRelatorioClickListener() {
            @Override
            public void onRelatorioClick(View view, int position) {

                RelatorioProducaoLeite producaoLeite = listarRelatorios.get(position);
                Fragment_RelatorioOrdenha relatorioOrdenha = new Fragment_RelatorioOrdenha(producaoLeite.getIdRelatorio());

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPrincipal,relatorioOrdenha).addToBackStack(null).commit();

            }

            @Override
            public void onRelatorioLongClick(View view, int position) {

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

        databaseRelatorio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listarRelatorios.clear();

                for(DataSnapshot dataSnapshotRelatorio: dataSnapshot.getChildren()){

                    RelatorioProducaoLeite relatorio2 = dataSnapshotRelatorio.getValue(RelatorioProducaoLeite.class);
                    listarRelatorios.add(relatorio2);
                }

                recyclerViewRelatorio.setAdapter(new AdapterRelatorio(getContext(),listarRelatorios));

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                recyclerViewRelatorio.setLayoutManager(layoutManager);
                recyclerViewRelatorio.setItemAnimator(new DefaultItemAnimator());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
