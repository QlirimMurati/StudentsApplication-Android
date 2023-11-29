package com.example.studentsapplication;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;
import android.widget.*;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityLogin extends AppCompatActivity {

    private LogOutSession session;
    private SharedPreferences checkbox;
    private static final String PREFS_NAME="checkbox";
    Button btnLogin, btnSignUp;
    EditText loginUser,loginPass;
    TextView forgetPassword,userMenu;
    CheckBox cb;
    DatabaseStudentet db=new DatabaseStudentet(this);
    Hashing h=new Hashing();
    static String tipi;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_login);
        checkbox=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        final SharedPreferences.Editor cbEditor=checkbox.edit();
        loginUser=(EditText)findViewById(R.id.userLogin);
        loginPass=(EditText)findViewById(R.id.passLogin);
        userMenu=(TextView)findViewById(R.id.userMenu);
        cb=(CheckBox)findViewById(R.id.loginCheckBox) ;
        forgetPassword=(TextView) findViewById(R.id.forgetPassword);
        firebaseAuth=FirebaseAuth.getInstance();
        getSavedData();
        session=new LogOutSession(this);
        if(session.loggedin()){
            Intent objhome = new Intent(MainActivityLogin.this, MainActivityHome.class);
            startActivity(objhome);
            finish();
        }
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=loginUser.getText().toString();
                if(user.equals("")){
                    Toast.makeText(MainActivityLogin.this,"Shenoni UserId fillimisht",Toast.LENGTH_LONG).show();
                }
                else{
                    String user1=db.searchUser(user);
                    if(user1.equals("1")){
                        ResetPassword.userReset=user;
                        Intent objhome = new Intent(MainActivityLogin.this, ResetPassword.class);
                        startActivity(objhome);
                    }
                    else{
                        Toast.makeText(MainActivityLogin.this,"Useri nuk ekziston",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String user=loginUser.getText().toString();
                String pass=loginPass.getText().toString();
                if(user.equals("")||pass.equals("")){
                    Toast.makeText(MainActivityLogin.this,"Mbushni fushat e zbrazeta",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(db.searchUser(user).equals(("1"))) {
                        String salt = db.searchSalt(user);
                        String passHash = pass + salt;
                        passHash = h.hashString(passHash, "SHA1");
                        String pasword = db.seatchPass(user);
                        if (passHash.equals(pasword)) {
                            session.setUser(user);
                            if(cb.isChecked()){
                                Boolean cbChecked=cb.isChecked();
                                cbEditor.putString("pref_user",loginUser.getText().toString());
                                cbEditor.putString("pref_pass",loginPass.getText().toString());
                                cbEditor.putBoolean("pref_checked",cbChecked);
                                cbEditor.apply();
                            }
                            else {
                                checkbox.edit().clear().apply();
                            }
                            cbEditor.putString("pref_tipi",db.prioriteti(user)).commit();
                            tipi=db.prioriteti(user);
                            session.setLoggedIn(true);
                            Intent objhome = new Intent(MainActivityLogin.this, MainActivityHome.class);
                            startActivity(objhome);
                            loginUser.getText().clear();
                            loginPass.getText().clear();
                        } else {
                            Toast.makeText(MainActivityLogin.this, "Fjalekalimi eshte gabim", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivityLogin.this, "Useri nuk ekziston", Toast.LENGTH_SHORT).show();
                    }
                }



            }

        });

        btnSignUp=(Button)findViewById(R.id.btnCreate);
        btnSignUp.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                Intent objCreate=new Intent(MainActivityLogin.this,MainActivitySignUp.class);
                startActivity(objCreate);
            }

        });

    }

    private void getSavedData(){
        SharedPreferences sp=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if(sp.contains("pref_user")){
            String u=sp.getString("pref_user","Not Found");
            loginUser.setText(u);
        }
        if(sp.contains("pref_pass")){
            String p=sp.getString("pref_pass","Not Found");
            loginPass.setText(p);
        }
        if(sp.contains("pref_checked")){
            Boolean b=sp.getBoolean("pref_checked",false);
            cb.setChecked(b);
        }
        if(sp.contains("pref_tipi")){
            String t=sp.getString("pref_tipi","Not Found");
            tipi=t;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}

