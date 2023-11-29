package com.example.studentsapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DrejtimetAdapter extends RecyclerView.Adapter<DrejtimetAdapter.DrejtimetViewHolder> {

     ArrayList<Drejtimet> sdrejtimet;
    public static Context context;

     public DrejtimetAdapter(ArrayList<Drejtimet> drejtimet,Context c){

         sdrejtimet=drejtimet;
         context=c;
     }

    @NonNull
    @Override
    public DrejtimetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.drejtimet_literatura_card,parent,false);
        DrejtimetViewHolder dvh=new DrejtimetViewHolder(v);
        return dvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DrejtimetViewHolder holder, int position) {
     Drejtimet drejtimi=sdrejtimet.get(position);
     holder.imgLiteratura.setImageResource(drejtimi.getImgResource());
     holder.txtLiteratura.setText(drejtimi.getDrejtimi());
     holder.txtLiteratura1.setText(drejtimi.getPershkrimi());
    }
    @Override
    public int getItemCount() {
        return sdrejtimet.size();
    }

    public void setfilter(ArrayList<Drejtimet> filterList){
        sdrejtimet=new ArrayList<>();
        sdrejtimet.addAll(filterList);
        notifyDataSetChanged();
    }

    public static class DrejtimetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView  imgLiteratura;
        public TextView   txtLiteratura;
        public  TextView  txtLiteratura1;
        public DrejtimetViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLiteratura =itemView.findViewById(R.id.imgLiteratura);
            txtLiteratura=itemView.findViewById(R.id.txtLiteratura);
            txtLiteratura1=itemView.findViewById(R.id.txtLiteratura1);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            if(position==0){
                Intent kompjuterike=new Intent(context,Kompjuterike.class);
                context.startActivity(kompjuterike);
            }
           else if(position==1)
            {
                Intent elektronika=new Intent(context,Elektronika.class);
                context.startActivity(elektronika);
            }
          else  if(position==2){
                Intent energjetika=new Intent(context,Energjetika.class);
                context.startActivity(energjetika);
            }
           else if(position==3){
                Intent automatika=new Intent(context,Automatika.class);
                context.startActivity(automatika);
            }
           else{
                Intent telekomi=new Intent(context,Telekomunikacioni.class);
                context.startActivity(telekomi);
            }
        }
    }



}
