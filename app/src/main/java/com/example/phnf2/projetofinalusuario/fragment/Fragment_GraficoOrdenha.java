package com.example.phnf2.projetofinalusuario.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.phnf2.projetofinalusuario.R;
import com.example.phnf2.projetofinalusuario.modelo.Ordenha;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Fragment_GraficoOrdenha extends Fragment {

    String id;
    String idOrdenha;
    GraphView graphView;
    DatabaseReference databaseOrdenha;
    FirebaseDatabase mFirebase;
    BarGraphSeries<DataPoint> graphSeriesBar;
    LineGraphSeries<DataPoint> graphSeriesLinear;
    Query query;
    Button kkk;
    private final static int PERMISSION_REQUEST = 11;

    @SuppressLint("ValidFragment")
    public Fragment_GraficoOrdenha(String id, String idOrdenha) {
        this.id = id;
        this.idOrdenha = idOrdenha;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__grafico_ordenha, container, false);



        graphView = view.findViewById(R.id.GraficoOrdenha);
        kkk = view.findViewById(R.id.buttonkkk);

        mFirebase = FirebaseDatabase.getInstance();

        if(mFirebase == null) {
            mFirebase.setPersistenceEnabled(true);
            query = mFirebase.getReference("Ordenhas").child(id).orderByChild("idOrdenha").equalTo(idOrdenha);

        }else{
            query = mFirebase.getReference("Ordenhas").child(id).orderByChild("idOrdenha").equalTo(idOrdenha);

            query.keepSynced(true);
        }


        graphSeriesBar = new BarGraphSeries<>();


        graphView.addSeries(graphSeriesBar);

        graphSeriesBar.setDrawValuesOnTop(true);

        graphSeriesBar.setValuesOnTopColor(Color.RED);

        graphSeriesBar.setSpacing(50);

        graphSeriesBar.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });



        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(9);

        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(100);

        graphSeriesBar.setTitle("Variáveis da Ordenha");

        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);


        graphSeriesBar.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {

                if(dataPoint.getX() == 0){
                    Toast.makeText(getActivity(), "Gráfico: Barra de Gor: "+dataPoint, Toast.LENGTH_SHORT).show();
                }else if(dataPoint.getX() == 1){
                    Toast.makeText(getActivity(), "Gráfico: Barra de Prot: "+dataPoint, Toast.LENGTH_SHORT).show();
                }else if(dataPoint.getX() == 2){
                    Toast.makeText(getActivity(), "Gráfico: Barra de Cas: "+dataPoint, Toast.LENGTH_SHORT).show();
                }else if(dataPoint.getX() == 3){
                    Toast.makeText(getActivity(), "Gráfico: Barra de Lact: "+dataPoint, Toast.LENGTH_SHORT).show();
                }else if(dataPoint.getX() == 4){
                    Toast.makeText(getActivity(), "Gráfico: Barra de St: "+dataPoint, Toast.LENGTH_SHORT).show();
                }else if(dataPoint.getX() == 5){
                    Toast.makeText(getActivity(), "Gráfico: Barra de Esd: "+dataPoint, Toast.LENGTH_SHORT).show();
                }else if(dataPoint.getX() == 6){
                    Toast.makeText(getActivity(), "Gráfico: Barra de Nu: "+dataPoint, Toast.LENGTH_SHORT).show();
                }else if(dataPoint.getX() == 7){
                    Toast.makeText(getActivity(), "Gráfico: Barra de Cel: "+dataPoint, Toast.LENGTH_SHORT).show();
                }else if(dataPoint.getX() == 8) {
                    Toast.makeText(getActivity(), "Gráfico: Barra de Ccs: " + dataPoint, Toast.LENGTH_SHORT).show();
                }

            }
        });

        kkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // graphView.takeSnapshotAndShare(getContext(), "exampleGraph", "GraphViewSnapshot");

                Toast.makeText(getContext(), "Tirar Foto - Em Implementação !", Toast.LENGTH_SHORT).show();
            }
        });


        //Colocar "legenda" na horizontal do grafico
//        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
//        staticLabelsFormatter.setHorizontalLabels(new String[]{"Gor","Prot","Cas","Lact","St","Esd","Nu","Cel","Ccs"});
//        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        ///Metodo para tirar foto
//        graphView.takeSnapshotAndShare(getContext(),"GraficoOrdenha","Grafico");
//
//        Bitmap bitmap = graphView.takeSnapshot();



        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        //Receber dados do Firebase e plotar no grafico... Espero que der certo.
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DataPoint[] dpBar = new DataPoint[9];
                double gord = 0, prot = 0, cas = 0, lact = 0, st = 0, esd = 0, nu = 0, cel = 0, ccs = 0;
                List<Double> doubleList = new ArrayList<>();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    Ordenha ordenha = dataSnapshot1.getValue(Ordenha.class);

                    gord = ordenha.getGord();
                    prot = ordenha.getProt();
                    cas = ordenha.getCas();
                    lact = ordenha.getLact();
                    st  = ordenha.getSt();
                    esd = ordenha.getEsd();
                    nu = ordenha.getNu();
                    cel = ordenha.getCel();
                    ccs = ordenha.getCcs();

                }

                doubleList.add(gord);
                doubleList.add(prot);
                doubleList.add(cas);
                doubleList.add(lact);
                doubleList.add(st);
                doubleList.add(esd);
                doubleList.add(nu);
                doubleList.add(cel);
                doubleList.add(ccs);


                Log.i("Size","Tamanho:"+doubleList.size());

                for(int i=0;i<doubleList.size();i++){
                    dpBar[i] = new DataPoint(i, doubleList.get(i));
                    Log.i("T","k"+dpBar[i]);
                }

                graphSeriesBar.resetData(dpBar);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
