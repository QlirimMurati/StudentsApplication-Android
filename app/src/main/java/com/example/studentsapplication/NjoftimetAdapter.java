package com.example.studentsapplication;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.example.studentsapplication.DatabasePath.databasepath;

public class NjoftimetAdapter extends RecyclerView.Adapter<NjoftimetAdapter.NjoftimetViewHolder> {

    ArrayList<UploadNjoftime> njoftimetList;
    Context context;
    String nameMateriali;
     String dateMateriali;
     String typeMateriali;
     String urlMateriali;
     String key;


    public NjoftimetAdapter(ArrayList<UploadNjoftime> a, Context c){
        njoftimetList=a;
        context=c;
    }

    @NonNull
    @Override
    public NjoftimetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new NjoftimetViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_njoftimet,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NjoftimetViewHolder holder, int i) {
        holder.njoftimiLenda.setText(njoftimetList.get(i).getNjoftimiLenda());
        holder.njoftimiPershkrimi.setText(njoftimetList.get(i).getNjoftimiPershkrimi());
        holder.njoftimiData.setText(njoftimetList.get(i).getNjoftimiData());

        if(njoftimetList.get(i).getNjoftimiType().equals("jpg")||njoftimetList.get(i).getNjoftimiType().equals("png")||njoftimetList.get(i).getNjoftimiType().equals("jpeg")||njoftimetList.get(i).getNjoftimiType().equals("gif") ||njoftimetList.get(i).getNjoftimiType().equals("bmp")){
            holder.njoftimiType.setImageResource(R.drawable.image1);
        }
        else if((njoftimetList.get(i).getNjoftimiType()).equals("pdf")){
            holder.njoftimiType.setImageResource(R.drawable.pdf1);
        }
        else if (njoftimetList.get(i).getNjoftimiType().equals("docx")||njoftimetList.get(i).getNjoftimiType().equals("doc")){
            holder.njoftimiType.setImageResource(R.drawable.word);
        }
        else{
            holder.njoftimiType.setImageResource(R.drawable.file);
        }
        key=njoftimetList.get(i).getNjoftimiID();
        nameMateriali=njoftimetList.get(i).getNjoftimiLenda();
        dateMateriali=njoftimetList.get(i).getNjoftimiData();
         typeMateriali=njoftimetList.get(i).getNjoftimiType();
        urlMateriali=njoftimetList.get(i).getNjoftimiUrl();

    }

    @Override
    public int getItemCount() {
        return njoftimetList.size();
    }


    class NjoftimetViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener  {
        ImageView njoftimiType;
        TextView njoftimiLenda;
        TextView njoftimiPershkrimi;
        TextView njoftimiData;

        public NjoftimetViewHolder(@NonNull View itemView){
            super(itemView);
            njoftimiType=itemView.findViewById(R.id.njoftimeImg);
            njoftimiLenda=itemView.findViewById(R.id.njoftimeLenda);
            njoftimiPershkrimi=itemView.findViewById(R.id.njoftimePershkrimi);
            njoftimiData=itemView.findViewById(R.id.njoftimeData);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            downloadFile(nameMateriali,typeMateriali,DIRECTORY_DOWNLOADS,urlMateriali);
        }
        @Override
        public boolean onLongClick(View v) {
           final int i= getAdapterPosition();
           try {
               if (MainActivityLogin.tipi.equals("a")) {
                   final Dialog dialog = new Dialog(context);
                   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                   dialog.setContentView(R.layout.customtitlebar);
                   Button delete = dialog.findViewById(R.id.fshijNjoftimin);
                   Button cancel = dialog.findViewById(R.id.cancelNjoftimin);
                   delete.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           deleteFile(njoftimetList.get(i).getNjoftimiID(), i);
                           dialog.dismiss();
                       }
                   });
                   cancel.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dialog.dismiss();
                       }
                   });
                   dialog.show();
               } else {
                   final Dialog dialog = new Dialog(context);
                   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                   dialog.setContentView(R.layout.informacionalert);
                   Button OK = dialog.findViewById(R.id.OK);
                   TextView info = dialog.findViewById(R.id.materialiMessage);
                   info.setText("Nuk keni autorizimin per te fshire!");
                   OK.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dialog.dismiss();
                       }
                   });
                   dialog.show();
               }
           }
           catch (Exception e){
               Toast.makeText(context,"Nuk mund te fshihet fajlli, Ri-Kyquni",Toast.LENGTH_SHORT).show();
           }
            return false;
        }

    }

    public void  setfilter(ArrayList<UploadNjoftime> filterList){
        njoftimetList=new ArrayList<>();
        njoftimetList.addAll(filterList);
        notifyDataSetChanged();
    }

    public void deleteFile(String id,int i){
        FirebaseDatabase.getInstance().getReference(databasepath).child(id).removeValue();
        njoftimetList.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, njoftimetList.size());
        Toast.makeText(context, "E dhena u fshi", Toast.LENGTH_SHORT).show();
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


}
