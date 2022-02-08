package com.example.mobilgarson;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilgarson.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText editPhone, editName, editPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editPhone = findViewById(R.id.edit_PhoneUp);
        editName = findViewById(R.id.edit_NameUp);
        editPassword = findViewById(R.id.edit_PasswordUp);
        btnSignUp = findViewById(R.id.btn_signUp);

        final String phonenumber = getIntent().getStringExtra("phonenumber");
        editPhone.setText(phonenumber);


        //Firebase Başlat
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);

                mDialog.setMessage("Lütfen Bekleyiniz...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {



                            if (editPassword.getText().toString().length()<8||editPassword.getText().toString().length()>16)
                            {
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this,"Şifre 8 ile 16 karakter arası uzunlukta olmalı",Toast.LENGTH_SHORT).show();
                            }else {

                                mDialog.dismiss();
                                User user = new User(editName.getText().toString(), editPassword.getText().toString());
                                table_user.child(phonenumber).setValue(user);
                                Toast.makeText(SignUp.this, "Kayıt Başarılı!!!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                startActivity(intent);
                            }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
