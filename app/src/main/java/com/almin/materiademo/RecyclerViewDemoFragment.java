package com.almin.materiademo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Almin on 2015/11/1.
 */
public class RecyclerViewDemoFragment extends Fragment{
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycle_demo,container,false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getActivity()));
        return mRecyclerView;
    }


    private static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
        private Context mContext;
        public RecyclerViewAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, int position) {

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(holder.mView, "translationZ", 20, 0);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animator.start();
                }
            });

            final TextInputLayout tilPassword = (TextInputLayout) holder.mView.findViewById(R.id.til_password);
            final TextInputLayout tilUsername = (TextInputLayout) holder.mView.findViewById(R.id.til_username);
            tilPassword.setHint("password");
            tilUsername.setHint("username");
            tilUsername.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() > 4) {
                        tilUsername.setError("username error");
                        tilUsername.setErrorEnabled(true);
                    } else {
                        tilUsername.setErrorEnabled(false);
                    }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View mView;
            public ViewHolder(View view) {
                super(view);
                mView = view;
            }
        }
    }


}
