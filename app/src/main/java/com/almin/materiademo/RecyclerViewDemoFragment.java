package com.almin.materiademo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Created by Almin on 2015/11/1.
 */
public class RecyclerViewDemoFragment extends Fragment{
    private static final int[] IMAGE_RES ={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.fi,R.drawable.g,R.drawable.s};
    private static final String BUNDLE_KEY_POSITION = "pos";
    private RecyclerView mRecyclerView;

    public static RecyclerViewDemoFragment newInstance(int position){
        RecyclerViewDemoFragment fragment = new RecyclerViewDemoFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(BUNDLE_KEY_POSITION, position);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycle_demo, container, false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), getArguments().getInt(BUNDLE_KEY_POSITION));
        mRecyclerView.setAdapter(adapter);
        return mRecyclerView;
    }


    private static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
        private Context mContext;
        private int mCount = 8;
        private int mPos;

        public RecyclerViewAdapter(Context mContext,int pos) {
            this.mContext = mContext;
            mPos = pos;
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mImageView.setBackgroundResource(IMAGE_RES[mPos]);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(holder.mView, "translationZ", 20, 0);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mContext.startActivity(new Intent(mContext, CollapsingDemoActivity.class));
                        }
                    });
                    animator.start();
                }
            });
            holder.mTilUsername.setHint("username");
            holder.mTilUsername.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() > 4) {
                        holder.mTilUsername.setError("username error");
                        holder.mTilUsername.setErrorEnabled(true);
                    } else {
                        holder.mTilUsername.setErrorEnabled(false);
                    }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });


            holder.mSbCornerRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (b) {
                        holder.mView.setCardElevation(i);//shadow
                        holder.mView.setRadius(i);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            holder.mSbShadow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if(b){
                        holder.mView.setCardElevation(i);//shadow
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCount;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public CardView mView;
            private ImageView mImageView;
            public TextInputLayout mTilUsername;
            public SeekBar mSbCornerRadius,mSbShadow;
            public ViewHolder(CardView view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.imageView);
                mTilUsername = (TextInputLayout) view.findViewById(R.id.til_username);
                mSbCornerRadius = (SeekBar) view.findViewById(R.id.seek_radius);
                mSbShadow = (SeekBar) view.findViewById(R.id.seek_shadow);
            }
        }
    }

}
