package com.example.mobilgarson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PasswordActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference details;
    Button newPassword;
    EditText txtPassword2,txtPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
         newPassword =findViewById(R.id.btn_verify);
         txtPassword=findViewById(R.id.new_Password);
         txtPassword2=findViewById(R.id.new_Password2);


        database = FirebaseDatabase.getInstance();
        details = database.getReference("User");

        //Şifreyi değiştirmek için resetpassword metoduna telefon numarası referans olarak göndermektedir
        newPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetPassword(getIntent().getStringExtra("phonenumber"));



            }
        });



    }
    //Şifre sıfırlama methodu
    private void resetPassword(String phonenumber) {



        if (txtPassword.getText().toString().equals(txtPassword2.getText().toString()))
        {

            if (txtPassword.getText().toString().length()<8||txtPassword.getText().toString().length()>16)
            {

                Toast.makeText(PasswordActivity.this,"Şifre 8 ile 16 karakter arası uzunlukta olmalı",Toast.LENGTH_SHORT).show();}

            else {details.child(phonenumber).child("password").setValue(txtPassword.getText().toString());
            Toast.makeText(PasswordActivity.this, "Şifre başarı ile değiştirildi!!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
            startActivity(intent);}
        }
        else {
            Toast.makeText(PasswordActivity.this, "Hatalı şifre girdiniz", Toast.LENGTH_SHORT).show();



        }




    }
}
