package com.example.studentsapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class SyllabusiAdapter extends RecyclerView.Adapter<SyllabusiAdapter.SyllabusiViewHolder> {
    ArrayList<Upload> syllabusiList;
    Context context;
    public SyllabusiAdapter(ArrayList<Upload> s,Context c){
        syllabusiList=s;
        context=c;
    }
    @NonNull
    @Override
    public SyllabusiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new SyllabusiViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_syllabusi,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull SyllabusiViewHolder holder, final int i) {
        holder.syllabusi.setText(syllabusiList.get(i).getEmriMateriali());
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(syllabusiList.get(i).getEmriMateriali(),"pdf",DIRECTORY_DOWNLOADS,syllabusiList.get(i).getUrlMateriali());
            }
        });
    }


    public void downloadFile(String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName +"."+ fileExtension);
        downloadmanager.enqueue(request);
        Toast.makeText(context, "Shkarkimi filloi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return syllabusiList.size();
    }

    public void  setfilter(ArrayList<Upload> filterList){
        syllabusiList=new ArrayList<>();
        syllabusiList.addAll(filterList);
        notifyDataSetChanged();
    }

    class SyllabusiViewHolder extends RecyclerView.ViewHolder {
        TextView syllabusi;
        ImageView download;

        public SyllabusiViewHolder(@NonNull View itemView) {
         super(itemView);
         syllabusi=itemView.findViewById(R.id.txtMaterialiName);
         download=itemView.findViewById(R.id.imgDownMateriali);

        }
    }

}
