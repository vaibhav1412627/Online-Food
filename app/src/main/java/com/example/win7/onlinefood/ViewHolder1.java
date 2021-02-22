package com.example.win7.onlinefood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by win7 on 20/01/2020.
 */

public class ViewHolder1 extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolder1(final View itemView) {
        super(itemView);

        mView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
        //item long click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });

    }

    //set details to recycler view row
    public void setDetails(Context ctx, String image, String name){
        //Views
        ImageView mImageIv = (ImageView) mView.findViewById(R.id.rImageView1);
        TextView  mtxtName = (TextView) mView.findViewById(R.id.txtName);



        //set data to views
        Picasso.with(ctx).load(image).into(mImageIv);
        mtxtName.setText(name);



    }

    private ViewHolder.ClickListener mClickListener;

    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
