
package com.example.studentsapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.studentsapplication.R;

import static com.example.studentsapplication.Web_Page.url_web_page;

public class MainActivityHome extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener{
    WebSettings webSettings;
    android.support.v7.widget.GridLayout mainGrid;
    WebView webview;
    TextView userMenu;
    private LogOutSession session;
    DrawerLayout drawer;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);
        session=new LogOutSession(this);
        if(!session.loggedin()){
            logout();
        }
        String setUserMenu= session.getUser();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         drawer = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
      webview=(WebView)findViewById(R.id.webview);
        mainGrid = ( android.support.v7.widget.GridLayout) findViewById(R.id.mainGrid);
        userMenu = (TextView) headerView.findViewById(R.id.userMenu);
        userMenu.setText("User:"+" "+session.getUser());
        //Set Event
        setToggleEvent(mainGrid);
        setSingleEvent(mainGrid);
    }


    private void setToggleEvent(android.support.v7.widget.GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final android.support.v7.widget.CardView  cardView = (android.support.v7.widget.CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        Toast.makeText(MainActivityHome.this, "State : True", Toast.LENGTH_SHORT).show();

                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        Toast.makeText(MainActivityHome.this, "State : False", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setSingleEvent(android.support.v7.widget.GridLayout mainGrid) {
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            android.support.v7.widget.CardView cardView = (android.support.v7.widget.CardView) mainGrid.getChildAt(i);
            final int cardNum = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(cardNum==0) {
                       Intent literatura=new Intent(MainActivityHome.this, DrejtimetMain.class);
                       startActivity(literatura);
                   }
                   else if (cardNum==1){
                       Intent syllabusi=new Intent(MainActivityHome.this, Syllabusi.class);
                       startActivity(syllabusi);
                    }
                  else  if (cardNum==2){
                       Intent njoftime=new Intent(MainActivityHome.this, Njoftime.class);
                       startActivity(njoftime);
                   }
                  else   if (cardNum==3){
                       Intent about=new Intent(MainActivityHome.this, AboutUs.class);
                       startActivity(about);
                   }
                 else   if (cardNum==4){
                       Intent intent=new Intent(MainActivityHome.this, Facebook.class);
                       startActivity(intent);

                   }
                   else if (cardNum==5) {
                       url_web_page="https://notimi.uni-pr.edu/";
                       Intent intent=new Intent(MainActivityHome.this, Web_Page.class);
                       startActivity(intent);

                    }


                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent=new Intent(MainActivityHome.this, MainActivityHome.class);
            startActivity(intent);
        }
        else  if (id == R.id.literatura) {
            Intent intent=new Intent(MainActivityHome.this, DrejtimetMain.class);
            startActivity(intent);
        }
        else if (id == R.id.syllabusi) {
            Intent intent=new Intent(MainActivityHome.this, Syllabusi.class);
            startActivity(intent);
        } else if (id == R.id.njoftime) {
            Intent intent=new Intent(MainActivityHome.this, Njoftime.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            Intent intent=new Intent(MainActivityHome.this, AboutUs.class);
            startActivity(intent);
        } else if (id == R.id.facebook) {
            Intent intent=new Intent(MainActivityHome.this, Facebook.class);
            startActivity(intent);
        } else if (id == R.id.online) {
            url_web_page="https://notimi.uni-pr.edu/";
            Intent intent=new Intent(MainActivityHome.this, Web_Page.class);
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
        Toast.makeText(MainActivityHome.this,"User logged out",Toast.LENGTH_LONG).show();
        startActivity(new Intent(MainActivityHome.this,MainActivityLogin.class));
    }

}
