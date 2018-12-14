package com.example.phnf2.projetofinalusuario.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.phnf2.projetofinalusuario.R;
import com.example.phnf2.projetofinalusuario.modelo.Usuario;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Inicio extends Fragment {

    private FirebaseDatabase mFirebase;
    private DatabaseReference mReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth firebaseAuth;
    private String mUsername;
    public final static int CODIGO_LOGAR = 5;
    private static final String ANONYMOUS = "anonymous";
    TextView textViewStatus;
    TextView textViewEmail;
    CircleImageView circleImgUser;

    public Fragment_Inicio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__inicio, container, false);


        textViewStatus = view.findViewById(R.id.status);
        textViewEmail = view.findViewById(R.id.emailUser);
        circleImgUser = view.findViewById(R.id.fotoUser);

        firebaseAuth = FirebaseAuth.getInstance();
        mUsername = ANONYMOUS;

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //logado
                    onSignInInitialize(user.getUid(),user.getDisplayName(),user.getEmail(),user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null);
                } else {
                    //não-logado
                    onSignOutCleanUp();



                    //chama o fluxo de login
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setLogo(R.drawable.dd)
                                    .setAvailableProviders(
                                            Arrays.asList(
                                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                                    new AuthUI.IdpConfig.TwitterBuilder().build()))
                                    .build(),
                            CODIGO_LOGAR);
                }
            }
        };


        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
        detachDatabaseReadListener();

    }

    public void onSignInInitialize(String id, String userName,String email,String foto) {

           textViewStatus.setText("Seja Bem-vindo " + firebaseAuth.getCurrentUser().getDisplayName());
           textViewEmail.setText(email);

           loadProfileIcon(foto, circleImgUser);

           mFirebase = FirebaseDatabase.getInstance();
           mReference = mFirebase.getReference("Usuarios");

           Usuario usuarioAtual = new Usuario(id, userName,email,foto);
           mReference.child(id).setValue(usuarioAtual);

           mUsername = userName;
           attackDatabaseReadListener();
    }


    public void onSignOutCleanUp() {
        mUsername = ANONYMOUS;
        detachDatabaseReadListener();
    }

    private void attackDatabaseReadListener() {
        if (mChildEventListener != null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mReference.addChildEventListener(mChildEventListener);
        }
    }


    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_LOGAR) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getContext(), "Eai,"+firebaseAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }


    public static void loadProfileIcon(String url, ImageView imageView) {
        Context context = imageView.getContext();
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.common_full_open_on_phone)
                        .dontAnimate()
                        .fitCenter())
                .into(imageView);
    }
}
