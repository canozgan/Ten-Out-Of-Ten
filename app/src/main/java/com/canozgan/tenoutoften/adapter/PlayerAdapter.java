package com.canozgan.tenoutoften.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.canozgan.tenoutoften.databinding.RecyclerviewRowBlueBinding;
import com.canozgan.tenoutoften.databinding.RecyclerviewRowGreenBinding;
import com.canozgan.tenoutoften.databinding.RecyclerviewRowRedBinding;
import com.canozgan.tenoutoften.model.PlayerModel;
import com.canozgan.tenoutoften.view.ShowPlayerCardActivity;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<PlayerModel> Players;
    public PlayerAdapter(ArrayList<PlayerModel> Players) {
        this.Players = Players;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0){
            RecyclerviewRowRedBinding binding=RecyclerviewRowRedBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return new PlayerViewHolderRed(binding);
        }
        else if(viewType==1){
            RecyclerviewRowGreenBinding binding=RecyclerviewRowGreenBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return new PlayerViewHolderGreen(binding);
        }
        else{
            RecyclerviewRowBlueBinding binding=RecyclerviewRowBlueBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return new PlayerViewHolderBlue(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PlayerViewHolderRed){
            ((PlayerViewHolderRed) holder).binding.textViewRecyclerRow.setText(Players.get(holder.getAdapterPosition()).name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(), ShowPlayerCardActivity.class);
                    intent.putExtra("id",Players.get(holder.getAdapterPosition()).id);
                    view.getContext().startActivity(intent);
                }
            });
        }
        else if(holder instanceof PlayerViewHolderGreen){
            ((PlayerViewHolderGreen) holder).binding.textViewRecyclerRow.setText(Players.get(holder.getAdapterPosition()).name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(), ShowPlayerCardActivity.class);
                    intent.putExtra("id",Players.get(holder.getAdapterPosition()).id);
                    view.getContext().startActivity(intent);
                }
            });
        }
        else{
            ((PlayerViewHolderBlue) holder).binding.textViewRecyclerRow.setText(Players.get(holder.getAdapterPosition()).name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(), ShowPlayerCardActivity.class);
                    intent.putExtra("id",Players.get(holder.getAdapterPosition()).id);
                    view.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(Players.get(position).id%3==0){
            return 0;
        }
        else if(Players.get(position).id%3==1){
            return 1;
        }
        else{
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return Players.size();
    }

    public class PlayerViewHolderRed extends RecyclerView.ViewHolder{
        RecyclerviewRowRedBinding binding;
        public PlayerViewHolderRed(RecyclerviewRowRedBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
    public class PlayerViewHolderGreen extends RecyclerView.ViewHolder{
        RecyclerviewRowGreenBinding binding;
        public PlayerViewHolderGreen(RecyclerviewRowGreenBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
    public class PlayerViewHolderBlue extends RecyclerView.ViewHolder{
        RecyclerviewRowBlueBinding binding;
        public PlayerViewHolderBlue(RecyclerviewRowBlueBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }


}
