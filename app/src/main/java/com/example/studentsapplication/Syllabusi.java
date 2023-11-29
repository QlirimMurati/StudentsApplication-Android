package com.example.studentsapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.example.studentsapplication.DatabasePath.databasepath;
import static com.example.studentsapplication.Web_Page.url_web_page;

public class Syllabusi extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    StorageReference storageReference;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Upload> upload;
    SyllabusiAdapter adapter;
    android.support.v7.widget.SearchView sv;
    private LogOutSession session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabusi);
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
         databasepath="Syllabusi";

        displayUpload();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(databasepath);
    }


    public void displayUpload(){
        recyclerView=(RecyclerView)findViewById(R.id.syllabusiRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        upload=new ArrayList<Upload>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child(databasepath);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                upload.clear();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Upload u=dataSnapshot1.getValue(Upload.class);
                    upload.add(u);
                }
                adapter=new SyllabusiAdapter(upload,Syllabusi.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Syllabusi.this,"Probleme me Databaze",Toast.LENGTH_SHORT).show();
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
            Intent intent=new Intent(Syllabusi.this, MainActivityHome.class);
            startActivity(intent);
        }
        else  if (id == R.id.literatura) {
            Intent intent=new Intent(Syllabusi.this, DrejtimetMain.class);
            startActivity(intent);
        }
        else if (id == R.id.syllabusi) {
            Intent intent=new Intent(Syllabusi.this, Syllabusi.class);
            startActivity(intent);
        } else if (id == R.id.njoftime) {
            Intent intent=new Intent(Syllabusi.this, Njoftime.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            Intent intent=new Intent(Syllabusi.this, AboutUs.class);
            startActivity(intent);
        }  else if (id == R.id.facebook) {
            Intent intent=new Intent(Syllabusi.this, Facebook.class);
            startActivity(intent);

        } else if (id == R.id.online) {
            url_web_page="https://notimi.uni-pr.edu/";
            Intent intent=new Intent(Syllabusi.this, Web_Page.class);
            startActivity(intent);
        }  else if (id == R.id.logOut) {
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        session.setLoggedIn(false);
        finish();
        Toast.makeText(Syllabusi.this,"User logged out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Syllabusi.this,MainActivityLogin.class));
    }
}
