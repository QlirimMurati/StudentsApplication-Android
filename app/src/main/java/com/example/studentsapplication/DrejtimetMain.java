package com.example.studentsapplication;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.studentsapplication.R;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.studentsapplication.Web_Page.url_web_page;

public class DrejtimetMain extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
  public RecyclerView sRecyclerView;
  public DrejtimetAdapter sAdapter;
  public RecyclerView.LayoutManager sLayoutManager;
    private LogOutSession session;
    public ArrayList<Drejtimet> drejtimet;
    android.support.v7.widget.SearchView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drejtimet_literatura);
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

        drejtimet=new ArrayList<>();
        drejtimet.add(new Drejtimet(R.drawable.kompjuterika1,"Kompjuterika","Materiali i drejtimit te  Kompjuterike"));
        drejtimet.add(new Drejtimet(R.drawable.elektronika1,"Elektronika","Materiali i drejtimit te  Elektronikes"));
        drejtimet.add(new Drejtimet(R.drawable.energjetika1,"Energjetika","Materiali i drejtimit te  Energjetikes"));
        drejtimet.add(new Drejtimet(R.drawable.automatika1,"Automatika","Materiali i drejtimit te  Automatikes"));
        drejtimet.add(new Drejtimet(R.drawable.telekomunikacioni1,"Telekomunikacioni","Materiali i drejtimit te  Telekomit"));
        sRecyclerView=findViewById(R.id.literaturaRecycler);
        sRecyclerView.setHasFixedSize(true);
        sLayoutManager=new LinearLayoutManager(this);
        sRecyclerView.setLayoutManager(sLayoutManager);
        sAdapter=new DrejtimetAdapter(drejtimet,this);
        sRecyclerView.setAdapter(sAdapter);
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
                final ArrayList<Drejtimet> filtered=filter(drejtimet,newText);
                sAdapter.setfilter(filtered);
                return true;
            }
        });
        return true;
    }

    private ArrayList<Drejtimet> filter(ArrayList<Drejtimet> a,String query){
        query=query.toLowerCase();
        final ArrayList<Drejtimet> filtered=new ArrayList<>();
        for (Drejtimet model:a){
            final String text=model.getDrejtimi().toLowerCase();
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
            Intent intent=new Intent(DrejtimetMain.this, MainActivityHome.class);
            startActivity(intent);
        }
        else  if (id == R.id.literatura) {
            Intent intent=new Intent(DrejtimetMain.this, DrejtimetMain.class);
            startActivity(intent);
        }
        else if (id == R.id.syllabusi) {
            Intent intent=new Intent(DrejtimetMain.this, Syllabusi.class);
            startActivity(intent);
        } else if (id == R.id.njoftime) {
            Intent intent=new Intent(DrejtimetMain.this, Njoftime.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            Intent intent=new Intent(DrejtimetMain.this, AboutUs.class);
            startActivity(intent);
        } else if (id == R.id.facebook) {
            Intent intent=new Intent(DrejtimetMain.this, Facebook.class);
            startActivity(intent);
        } else if (id == R.id.online) {
            url_web_page="https://notimi.uni-pr.edu/";
            Intent intent=new Intent(DrejtimetMain.this, Web_Page.class);
            startActivity(intent);
        }
        else if (id == R.id.logOut) {
            logout();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void logout() {
        session.setLoggedIn(false);
        finish();
        Toast.makeText(DrejtimetMain.this,"User logged out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(DrejtimetMain.this,MainActivityLogin.class));
    }

}
