package com.hxx.shopping_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private ArrayList<item> MyList;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView MyTextview;
        public ViewHolder(View itemView){
            super(itemView);
            MyTextview = itemView.findViewById(R.id.title);

        }
    }

    public Adapter(ArrayList<item> list){
        MyList = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        item current_item = MyList.get(position);
        holder.MyTextview.setText(current_item.getText());
    }

    @Override
    public int getItemCount() {
        return MyList.size();
    }



}
