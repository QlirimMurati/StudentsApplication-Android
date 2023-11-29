package com.example.studentsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivitySignUp extends AppCompatActivity {

    DatabaseStudentet db=new DatabaseStudentet(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_signup);
        final Hashing h=new Hashing();
        final UsersStudentet u=new UsersStudentet();
        final EditText uname=(EditText)findViewById(R.id.createName);
        final EditText uuser=(EditText)findViewById(R.id.createID);
        final EditText uemail=(EditText)findViewById(R.id.createEmail);
        final EditText upass=(EditText)findViewById(R.id.createPass);
        final EditText upass1=(EditText)findViewById(R.id.createPass1);

        Button SingUp=(Button)findViewById(R.id.btnRegister);
        SingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=uname.getText().toString();
                String user=uuser.getText().toString();
                String email=uemail.getText().toString();
                String pass=upass.getText().toString();
                String pass1=upass1.getText().toString();
                String tipi="a"; //Per admin duhet te jete "a"

                if(name.equals("")||user.equals("")||email.equals("")||pass.equals("")){
                    Toast.makeText(MainActivitySignUp.this,"Mbushni fushat e zbrazta",Toast.LENGTH_SHORT).show();
                }
                else if((db.searchUser(user)).equals("1")){
                    Toast.makeText(MainActivitySignUp.this,"Ky User ekziston",Toast.LENGTH_SHORT).show();
                }
                else if(user.length()<5){
                    Toast.makeText(MainActivitySignUp.this,"Username duhet te permbaj se paku 5 karaktere",Toast.LENGTH_SHORT).show();
                }
                else if (!email.matches("^[A-Za-z0-9._\\-]+@[A-Za-z]+.[A-Za-z]{2,3}$")) {
                    Toast.makeText(MainActivitySignUp.this,"Emaili eshte gabim",Toast.LENGTH_SHORT).show();
                }
                else if(!pass.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$")){
                    Toast.makeText(MainActivitySignUp.this,"Fjalekalimi duhet te kete shkronje te madhe, te vogel dhe numer",Toast.LENGTH_LONG).show();
                }
                else if (!pass.equals(pass1)){
                    Toast.makeText(MainActivitySignUp.this,"Fjalekalime te ndryshme",Toast.LENGTH_SHORT).show();
                }

               else{

                    Integer user1=Integer.parseInt(uuser.getText().toString());
                    String salt=h.saltGenerator();
                    String passSalt=pass+salt;
                    u.setNameUsers(name);
                    u.setUsernameUsers(user1);
                    u.setEmailUsers(email);
                    u.setSaltUsers(salt);
                    u.setPrioritetiUsers(tipi);
                    u.setPasswordUsers(h.hashString(passSalt,"SHA1"));
                    db.insertUser(u);
                    Toast.makeText(MainActivitySignUp.this,"Llogaria u krijua",Toast.LENGTH_SHORT).show();
                    Intent objhome = new Intent(MainActivitySignUp.this, MainActivityLogin.class);
                    startActivity(objhome);
                    uname.getText().clear();
                     uuser.getText().clear();
                     uemail.getText().clear();
                    upass.getText().clear();
                    upass1.getText().clear();
                }
            }
        });
    }
}
