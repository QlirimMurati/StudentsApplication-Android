package com.example.studentsapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.graphics.Color.*;
import static com.example.studentsapplication.DatabasePath.*;
import static com.example.studentsapplication.Web_Page.url_web_page;


public class Automatika extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText editPdfName;
    ImageView fileUpload;
    String fajliZgjedhur;
    Button sem1;
    Button sem2;
    Button sem3;
    Button sem4;
    Button sem5;
    Button sem6;
    LogOutSession session;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Upload> upload;
    MaterialiAdapter adapter;
    android.support.v7.widget.SearchView sv;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatika);
        session=new LogOutSession(this);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        TextView userMenu=headerView.findViewById(R.id.userMenu);
        userMenu.setText("User:"+" "+session.getUser());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        databasepath="Automatika Semestri 1";
        recyclerView=findViewById(R.id.recyclerMaterialiComp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayUpload();
        editPdfName = (EditText) findViewById(R.id.txtuploadfile);
        fileUpload = (ImageView) findViewById(R.id.fileuploadimg);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(databasepath);
        sem1=(Button)findViewById(R.id.btnSem1);
        sem2=(Button)findViewById(R.id.btnSem2);
        sem3=(Button)findViewById(R.id.btnSem3);
        sem4=(Button)findViewById(R.id.btnSem4);
        sem5=(Button)findViewById(R.id.btnSem5);
        sem6=(Button)findViewById(R.id.btnSem6);


        fileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editPdfName.getText().toString().equals("")) {
                    final Dialog dialog=new Dialog(Automatika.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.informacionalert);
                    Button OK=dialog.findViewById(R.id.OK);
                    OK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                } else {
                    selectFile();
                }
            }
        });
        sem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databasepath="Automatika Semestri 1";
                sem1.setBackgroundColor(Color.WHITE);
                sem1.setTextColor(Color.parseColor("#2196f3"));
                sem2.setBackgroundColor(Color.parseColor("#2196f3"));
                sem2.setTextColor(Color.parseColor("#ffffff"));
                sem3.setBackgroundColor(Color.parseColor("#2196f3"));
                sem3.setTextColor(Color.parseColor("#ffffff"));
                sem4.setBackgroundColor(Color.parseColor("#2196f3"));
                sem4.setTextColor(Color.parseColor("#ffffff"));
                sem5.setBackgroundColor(Color.parseColor("#2196f3"));
                sem5.setTextColor(Color.parseColor("#ffffff"));
                sem6.setBackgroundColor(Color.parseColor("#2196f3"));
                sem6.setTextColor(Color.parseColor("#ffffff"));
                displayUpload();
            }
        });
        sem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databasepath="Automatika Semestri 2";
                sem2.setBackgroundColor(Color.WHITE);
                sem2.setTextColor(Color.parseColor("#2196f3"));
                sem1.setBackgroundColor(Color.parseColor("#2196f3"));
                sem1.setTextColor(Color.parseColor("#ffffff"));
                sem3.setBackgroundColor(Color.parseColor("#2196f3"));
                sem3.setTextColor(Color.parseColor("#ffffff"));
                sem4.setBackgroundColor(Color.parseColor("#2196f3"));
                sem4.setTextColor(Color.parseColor("#ffffff"));
                sem5.setBackgroundColor(Color.parseColor("#2196f3"));
                sem5.setTextColor(Color.parseColor("#ffffff"));
                sem6.setBackgroundColor(Color.parseColor("#2196f3"));
                sem6.setTextColor(Color.parseColor("#ffffff"));
                displayUpload();
            }
        });
        sem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databasepath="Automatika Semestri 3";
                sem3.setBackgroundColor(Color.WHITE);
                sem3.setTextColor(Color.parseColor("#2196f3"));
                sem2.setBackgroundColor(Color.parseColor("#2196f3"));
                sem2.setTextColor(Color.parseColor("#ffffff"));
                sem1.setBackgroundColor(Color.parseColor("#2196f3"));
                sem1.setTextColor(Color.parseColor("#ffffff"));
                sem4.setBackgroundColor(Color.parseColor("#2196f3"));
                sem4.setTextColor(Color.parseColor("#ffffff"));
                sem5.setBackgroundColor(Color.parseColor("#2196f3"));
                sem5.setTextColor(Color.parseColor("#ffffff"));
                sem6.setBackgroundColor(Color.parseColor("#2196f3"));
                sem6.setTextColor(Color.parseColor("#ffffff"));
                displayUpload();
            }
        });
        sem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               databasepath="Automatika Semestri 4";
                sem4.setBackgroundColor(Color.WHITE);
                sem4.setTextColor(Color.parseColor("#2196f3"));
                sem2.setBackgroundColor(Color.parseColor("#2196f3"));
                sem2.setTextColor(Color.parseColor("#ffffff"));
                sem3.setBackgroundColor(Color.parseColor("#2196f3"));
                sem3.setTextColor(Color.parseColor("#ffffff"));
                sem1.setBackgroundColor(Color.parseColor("#2196f3"));
                sem1.setTextColor(Color.parseColor("#ffffff"));
                sem5.setBackgroundColor(Color.parseColor("#2196f3"));
                sem5.setTextColor(Color.parseColor("#ffffff"));
                sem6.setBackgroundColor(Color.parseColor("#2196f3"));
                sem6.setTextColor(Color.parseColor("#ffffff"));
                displayUpload();
            }
        });
        sem5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               databasepath="Automatika Semestri 5";
                sem5.setBackgroundColor(Color.WHITE);
                sem5.setTextColor(Color.parseColor("#2196f3"));
                sem2.setBackgroundColor(Color.parseColor("#2196f3"));
                sem2.setTextColor(Color.parseColor("#ffffff"));
                sem3.setBackgroundColor(Color.parseColor("#2196f3"));
                sem3.setTextColor(Color.parseColor("#ffffff"));
                sem4.setBackgroundColor(Color.parseColor("#2196f3"));
                sem4.setTextColor(Color.parseColor("#ffffff"));
                sem1.setBackgroundColor(Color.parseColor("#2196f3"));
                sem1.setTextColor(Color.parseColor("#ffffff"));
                sem6.setBackgroundColor(Color.parseColor("#2196f3"));
                sem6.setTextColor(Color.parseColor("#ffffff"));
                displayUpload();
            }
        });
        sem6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databasepath="Automatika Semestri 6";
                sem6.setBackgroundColor(Color.WHITE);
                sem6.setTextColor(Color.parseColor("#2196f3"));
                sem2.setBackgroundColor(Color.parseColor("#2196f3"));
                sem2.setTextColor(Color.parseColor("#ffffff"));
                sem3.setBackgroundColor(Color.parseColor("#2196f3"));
                sem3.setTextColor(Color.parseColor("#ffffff"));
                sem4.setBackgroundColor(Color.parseColor("#2196f3"));
                sem4.setTextColor(Color.parseColor("#ffffff"));
                sem5.setBackgroundColor(Color.parseColor("#2196f3"));
                sem5.setTextColor(Color.parseColor("#ffffff"));
                sem1.setBackgroundColor(Color.parseColor("#2196f3"));
                sem1.setTextColor(Color.parseColor("#ffffff"));
                displayUpload();
            }
        });

    }

    private void selectFile() {
        final Dialog dialog=new Dialog(Automatika.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.uploadalert);
        TextView PDF=dialog.findViewById(R.id.uploadPDF);
        TextView Image=dialog.findViewById(R.id.uploadImage);
        TextView Word=dialog.findViewById(R.id.uploadWord);
        PDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fajliZgjedhur="pdf";
                Intent intent=new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Zgjedhni PDF fajlin"),1);
                dialog.dismiss();
            }
        });
        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fajliZgjedhur="image";
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Zgjedhni foton"),1);
                dialog.dismiss();
            }
        });
        Word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fajliZgjedhur="docx";
                Intent intent=new Intent();
                intent.setType("application/msword");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Zgjedhni Ms Word fajlin"),1);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //pasi tzgjidhet fajli thirret kjo metode
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uploadFile(data.getData());
        }
    }

    // pjesa per me marre extension te fajlit qe e uploadojme
    private String getExtension(Uri uri){
        String extension;
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        extension=mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        return extension;
    }

    //Data e uploadimit te fajlit
    private String DataUploadimit(){
        Date c= Calendar.getInstance().getTime();
        SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
        return df.format(c);
    }

    private void uploadFile(Uri data) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.progresalert,null);
        progressDialog.setCustomTitle(view);
        progressDialog.show();
        final String extension=getExtension(data);
        final  StorageReference reference=storageReference.child(databasepath+"/"+editPdfName.getText().toString()+"."+extension);
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(Automatika.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        String uploadId = databaseReference.push().getKey();
                        Upload upload = new Upload(editPdfName.getText().toString(),DataUploadimit(), extension,url,uploadId);
                        databaseReference.child(uploadId).setValue(upload);
                        Toast.makeText(Automatika.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded: "+(int)progress+"%");

            }
        });
    }
    public void displayUpload(){
        upload=new ArrayList<Upload>();
        databaseReference=FirebaseDatabase.getInstance().getReference().child(databasepath);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                upload.clear();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Upload u=dataSnapshot1.getValue(Upload.class);
                    upload.add(u);
                }
                adapter=new MaterialiAdapter(Automatika.this,upload);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Automatika.this,"Probleme me Databaze",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.searchview,menu);
        final MenuItem searchItem=menu.findItem(R.id.searchview);
        sv = (android.support.v7.widget.SearchView) searchItem.getActionView();
        ((EditText) sv.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.white));
        sv.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!sv.isIconified()){
                    sv.setIconified(true);
                }
                searchItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final ArrayList<Upload> filtered=filter(upload,newText);
                adapter.setfilter(filtered);
                return true;
            }
        });
        return true;
    }

    private ArrayList<Upload> filter(ArrayList<Upload> a,String query){
        query=query.toLowerCase();
        final ArrayList<Upload> filtered=new ArrayList<>();
        for (Upload model:a){
            final String text=model.getEmriMateriali().toLowerCase();
            if(text.startsWith(query)){
                filtered.add(model);
            }
        }
        return filtered;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent=new Intent(Automatika.this, MainActivityHome.class);
            startActivity(intent);
        }
        else  if (id == R.id.literatura) {
            Intent intent=new Intent(Automatika.this, DrejtimetMain.class);
            startActivity(intent);
        }
        else if (id == R.id.syllabusi) {
            Intent intent=new Intent(Automatika.this, Syllabusi.class);
            startActivity(intent);
        } else if (id == R.id.njoftime) {
            Intent intent=new Intent(Automatika.this, Njoftime.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            Intent intent=new Intent(Automatika.this, AboutUs.class);
            startActivity(intent);
        } else if (id == R.id.facebook) {
            Intent intent=new Intent(Automatika.this, Facebook.class);
            startActivity(intent);

        } else if (id == R.id.online) {
            url_web_page="https://notimi.uni-pr.edu/";
            Intent intent=new Intent(Automatika.this, Web_Page.class);
            startActivity(intent);
        }
         if (id == R.id.logOut) {
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        session.setLoggedIn(false);
        finish();
        Toast.makeText(Automatika.this,"User logged out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Automatika.this,MainActivityLogin.class));
    }

}
