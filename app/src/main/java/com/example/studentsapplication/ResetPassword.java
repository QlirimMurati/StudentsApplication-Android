package com.example.studentsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseException;

public class ResetPassword extends AppCompatActivity {

    static String userReset;
    DatabaseStudentet db=new DatabaseStudentet(this);
    Hashing h=new Hashing();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.resetpassword);

        Button btnReset=(Button)findViewById(R.id.resetBtn);
        final EditText pass=(EditText)findViewById(R.id.resetPass);
        final EditText pass1=(EditText)findViewById(R.id.resetPass1);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password=pass.getText().toString();
                String password1=pass1.getText().toString();

                if(password.equals("")||password1.equals("")){
                    Toast.makeText(ResetPassword.this,"Mbushni fushat",Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(password1)){
                    Toast.makeText(ResetPassword.this,"Fjalekalime te ndryshme",Toast.LENGTH_SHORT).show();
                }
                else{
                    String newpass=password;
                    String salt=h.saltGenerator();
                    String passHash=newpass+salt;
                    passHash=h.hashString(passHash,"SHA1");
                    try {
                        db.resetPass(userReset, passHash, salt);
                        Toast.makeText(ResetPassword.this,"Fjalekalimi u ndryshua",Toast.LENGTH_SHORT).show();
                        Intent objhome = new Intent(ResetPassword.this, MainActivityLogin.class);
                        startActivity(objhome);
                    }
                    catch (DatabaseException e){
                        Toast.makeText(ResetPassword.this,"Fjalekalimi nuk u ndryshua",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
