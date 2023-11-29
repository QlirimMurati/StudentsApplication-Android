package com.example.studentsapplication;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class Web_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    WebView webView;
    private LogOutSession session;
    static String   url_web_page="https://notimi.uni-pr.edu/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web__page);
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
        webView=(WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url_web_page);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent=new Intent(Web_Page.this, MainActivityHome.class);
            startActivity(intent);
        }
        else  if (id == R.id.literatura) {
            Intent intent=new Intent(Web_Page.this, DrejtimetMain.class);
            startActivity(intent);
        }
        else if (id == R.id.syllabusi) {
            Intent intent=new Intent(Web_Page.this, Syllabusi.class);
            startActivity(intent);
        } else if (id == R.id.njoftime) {
            Intent intent=new Intent(Web_Page.this, Njoftime.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            Intent intent=new Intent(Web_Page.this, AboutUs.class);
            startActivity(intent);
        } else if (id == R.id.facebook) {
            Intent intent=new Intent(Web_Page.this, Facebook.class);
            startActivity(intent);

        } else if (id == R.id.online) {
            url_web_page="https://notimi.uni-pr.edu/";
            Intent intent=new Intent(Web_Page.this, Web_Page.class);
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
        Toast.makeText(Web_Page.this,"User logged out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Web_Page.this,MainActivityLogin.class));
    }
}
