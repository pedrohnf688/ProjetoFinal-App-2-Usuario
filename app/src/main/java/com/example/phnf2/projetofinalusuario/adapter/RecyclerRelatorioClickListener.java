package com.example.phnf2.projetofinalusuario.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerRelatorioClickListener implements RecyclerView.OnItemTouchListener {

    RecyclerRelatorioClickListener.OnRelatorioClickListener mListener2;
    GestureDetector mGestureDetector2;


    public interface OnRelatorioClickListener {
        void onRelatorioClick(View view, int position);

        void onRelatorioLongClick(View view, int position);
    }


    public RecyclerRelatorioClickListener(Context context, final RecyclerView view, RecyclerRelatorioClickListener.OnRelatorioClickListener listener) {
        mListener2 = listener;
        mGestureDetector2 = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                super.onSingleTapUp(e);
                View childView = view.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mListener2 != null) {
                    mListener2.onRelatorioClick(childView, view.getChildAdapterPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);

                View childView = view.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mListener2 != null) {
                    mListener2.onRelatorioLongClick(childView, view.getChildAdapterPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        mGestureDetector2.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

