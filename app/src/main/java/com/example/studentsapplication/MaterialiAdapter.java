package com.example.studentsapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicMarkableReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static android.support.v4.content.ContextCompat.startActivity;
import static com.example.studentsapplication.DatabasePath.databasepath;

public class MaterialiAdapter extends RecyclerView .Adapter<MaterialiAdapter.MaterialiViewHolder> {


    Context context;
    ArrayList<Upload> uploadMateriali;


    public MaterialiAdapter(Context c, ArrayList<Upload> u){
        context=c;
        uploadMateriali=u;
    }

    @NonNull
    @Override
    public MaterialiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MaterialiViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_materiali,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MaterialiViewHolder materialiViewHolder, final int i) {

        materialiViewHolder.txtEmri.setText(uploadMateriali.get(i).getEmriMateriali());
        materialiViewHolder.txtUrl.setText(uploadMateriali.get(i).getDateMateriali());
        if(uploadMateriali.get(i).getTypeMateriali().equals("jpg")||uploadMateriali.get(i).getTypeMateriali().equals("png")||uploadMateriali.get(i).getTypeMateriali().equals("jpeg")||uploadMateriali.get(i).getTypeMateriali().equals("gif") ||uploadMateriali.get(i).getTypeMateriali().equals("bmp")){
            materialiViewHolder.typeFile.setImageResource(R.drawable.image1);
        }
        else if((uploadMateriali.get(i).getTypeMateriali()).equals("pdf")){
            materialiViewHolder.typeFile.setImageResource(R.drawable.pdf1);
        }
        else if (uploadMateriali.get(i).getTypeMateriali().equals("docx")||uploadMateriali.get(i).getTypeMateriali().equals("doc")){
            materialiViewHolder.typeFile.setImageResource(R.drawable.word);
        }
        else{
            materialiViewHolder.typeFile.setImageResource(R.drawable.file);
        }
        final String nameMateriali=uploadMateriali.get(i).getEmriMateriali();
        final String dateMateriali=uploadMateriali.get(i).getDateMateriali();
        final String typeMateriali=uploadMateriali.get(i).getTypeMateriali();
        final String urlMateriali=uploadMateriali.get(i).getUrlMateriali();
        final String idMateriali=uploadMateriali.get(i).getIdMateriali();

        materialiViewHolder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(nameMateriali,typeMateriali,DIRECTORY_DOWNLOADS,urlMateriali);
            }
        });
        materialiViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(MainActivityLogin.tipi.equals("a")) {
                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.materialicustomalert);
                        Button delete = dialog.findViewById(R.id.materialiDelete);
                        Button cancel = dialog.findViewById(R.id.materialiCancel);
                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteFile(databasepath, idMateriali, i);
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
                    }
                    else{
                        final Dialog dialog=new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.informacionalert);
                        Button OK=dialog.findViewById(R.id.OK);
                        TextView info=dialog.findViewById(R.id.materialiMessage);
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return uploadMateriali.size();
    }

    class MaterialiViewHolder extends RecyclerView.ViewHolder {
        ImageView download;
        ImageView typeFile;
        ImageView delete;
        TextView txtEmri;
        TextView txtUrl;

        public MaterialiViewHolder(@NonNull final View itemView) {
            super(itemView);
            typeFile = itemView.findViewById(R.id.imgFileType);
            download = itemView.findViewById(R.id.imgDownMateriali);
            delete = itemView.findViewById(R.id.imgDelMateriali);
            txtEmri = itemView.findViewById(R.id.txtMaterialiName);
            txtUrl = itemView.findViewById(R.id.txtMaterialiDate);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int i=getAdapterPosition();
                    String updateID=uploadMateriali.get(i).getIdMateriali();
                    String updateName=uploadMateriali.get(i).getEmriMateriali();
                    String updateDate=uploadMateriali.get(i).getDateMateriali();
                    String updateType=uploadMateriali.get(i).getTypeMateriali();
                    String updateUrl=uploadMateriali.get(i).getUrlMateriali();
                    updateAlert(updateID,databasepath,updateName,updateDate,updateType,updateUrl);
                    return false;
                }
            });
        }

    }

    public void setfilter(ArrayList<Upload> filterList){
        uploadMateriali=new ArrayList<>();
        uploadMateriali.addAll(filterList);
        notifyDataSetChanged();
    }


    public void updateAlert(final String id,final String ref, String name,  final String date, final String type, final String url){
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.update_firebase);
        final TextView editName=dialog.findViewById(R.id.nameEdit);
        try{
        if(!MainActivityLogin.tipi.equals("a")){
            editName.setEnabled(false);
        }}
        catch(Exception e){
            Toast.makeText(context, "Probleme me DB, Ri-Kyquni", Toast.LENGTH_SHORT).show();
        }
        TextView editDate=dialog.findViewById(R.id.dateEdit);
        TextView editType=dialog.findViewById(R.id.typeEdit);
        TextView editURL=dialog.findViewById(R.id.urlEdit);
        editName.setText(name);
        editDate.setText(date);
        editType.setText(type);
        editURL.setText(url);
        Button btnUpdate=dialog.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivityLogin.tipi.equals("a")) {
                    try {
                        String nameUpdate=editName.getText().toString();
                        if(nameUpdate.equals("")){
                            Toast.makeText(context, "Fusha per emertim duhet te mbushet", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            updateFirebase(id, ref, nameUpdate, date, type, url);
                            Toast.makeText(context, "Fajli u editua", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Editimi nuk u realizua", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void updateFirebase(String id,String ref,String name, String date,String type,String url){
        DatabaseReference dbr=FirebaseDatabase.getInstance().getReference(ref).child(id);
        Upload upload=new Upload(name,date,type,url,id);
        dbr.setValue(upload);
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


    private void deleteFile(String ref,String id, final int i) {
        FirebaseDatabase.getInstance().getReference(ref).child(id).removeValue();
        uploadMateriali.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, uploadMateriali.size());
        Toast.makeText(context, "E dhena u fshi", Toast.LENGTH_SHORT).show();
    }



}








