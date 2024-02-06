package com.kldv.gibought.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kldv.gibought.*;

import java.util.List;

public class ObgAdapter extends RecyclerView.Adapter < ObgAdapter.ObgViewHolder > {

    private final List< ObgItem > obgItems;

    public ObgAdapter(List < ObgItem > obgItems) {
        this.obgItems = obgItems;
    }

    @NonNull
    @Override
    public ObgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ObgViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ObgViewHolder holder, int position) {
        holder.setObgData(obgItems.get(position));
    }

    @Override
    public int getItemCount() {
        return obgItems.size();
    }

    static class ObgViewHolder extends RecyclerView.ViewHolder {

        private final TextView textTitle;
        private final TextView textDesc;
        private final ImageView imgObg;

        ObgViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDescription);
            imgObg = itemView.findViewById(R.id.imgObg);

        }
        void setObgData(ObgItem obgItem) {

            textTitle.setText(obgItem.getTitle());
            textDesc.setText(obgItem.getDesc());
            imgObg.setImageResource(obgItem.getImg());

        }
    }
}