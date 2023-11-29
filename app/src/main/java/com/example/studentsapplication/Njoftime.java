package com.example.studentsapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.webkit.MimeTypeMap;
import android.widget.*;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.studentsapplication.DatabasePath.databasepath;
import static com.example.studentsapplication.Web_Page.url_web_page;

public class Njoftime extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText fileUploadLenda;
    EditText fileUploadSubjekti;
    ImageView fileUploadImg;
    String fajliZgjedhur;

    android.support.v7.widget.SearchView sv;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<UploadNjoftime> upload;
    NjoftimetAdapter adapter;
    private LogOutSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_njoftime);
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
         databasepath="Njoftime";

        recyclerView=(RecyclerView)findViewById(R.id.recyclerNjoftimet);
       recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(databasepath);
        displayUpload();
        fileUploadLenda= (EditText) findViewById(R.id.contentLenda);
        fileUploadSubjekti= (EditText) findViewById(R.id.contentSubjekti);
        fileUploadImg=(ImageView) findViewById(R.id.fileuploadimg);


        fileUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fileUploadLenda.getText().toString().equals("")||fileUploadSubjekti.getText().toString().equals("")) {
                    final Dialog dialog=new Dialog(Njoftime.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.informacionalert);
                    TextView info=dialog.findViewById(R.id.materialiMessage);
                    info.setText("Vendosni infot e njoftimit!");
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
    }

    private void selectFile() {
        final Dialog dialog=new Dialog(Njoftime.this);
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
        final  StorageReference reference=storageReference.child(databasepath+"/"+fileUploadLenda.getText().toString()+"."+extension);
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(Njoftime.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        String uploadId = databaseReference.push().getKey();
                        UploadNjoftime upload = new UploadNjoftime(fileUploadLenda.getText().toString(),fileUploadSubjekti.getText().toString(),DataUploadimit(), extension,url,uploadId);
                        databaseReference.child(uploadId).setValue(upload);
                        Toast.makeText(Njoftime.this, "File Uploaded", Toast.LENGTH_SHORT).show();
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
        upload=new ArrayList<UploadNjoftime>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child(databasepath);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                upload.clear();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    UploadNjoftime u=dataSnapshot1.getValue(UploadNjoftime.class);
                    upload.add(u);
                }
                adapter=new NjoftimetAdapter(upload,Njoftime.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Njoftime.this,"Probleme me Databaze",Toast.LENGTH_SHORT).show();
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
                final ArrayList<UploadNjoftime> filtered=filter(upload,newText);
                adapter.setfilter(filtered);
                return true;
            }
        });
        return true;
    }

    private ArrayList<UploadNjoftime> filter(ArrayList<UploadNjoftime> a,String query){
        query=query.toLowerCase();
        final ArrayList<UploadNjoftime> filtered=new ArrayList<>();
        for (UploadNjoftime model:a){
            final String text=model.getNjoftimiLenda().toLowerCase();
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
            Intent intent=new Intent(Njoftime.this, MainActivityHome.class);
            startActivity(intent);
        }
        else  if (id == R.id.literatura) {
            Intent intent=new Intent(Njoftime.this, DrejtimetMain.class);
            startActivity(intent);
        }
        else if (id == R.id.syllabusi) {
            Intent intent=new Intent(Njoftime.this, Syllabusi.class);
            startActivity(intent);
        } else if (id == R.id.njoftime) {
            Intent intent=new Intent(Njoftime.this, Njoftime.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            Intent intent=new Intent(Njoftime.this, AboutUs.class);
            startActivity(intent);
        } else if (id == R.id.facebook) {
            Intent intent=new Intent(Njoftime.this, Facebook.class);
            startActivity(intent);

        } else if (id == R.id.online) {
            url_web_page="https://notimi.uni-pr.edu/";
            Intent intent=new Intent(Njoftime.this, Web_Page.class);
            startActivity(intent);
        } else if (id == R.id.logOut) {
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        session.setLoggedIn(false);
        finish();
        Toast.makeText(Njoftime.this,"User logged out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Njoftime.this,MainActivityLogin.class));
    }
}
