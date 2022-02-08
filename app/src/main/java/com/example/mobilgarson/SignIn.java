package com.example.mobilgarson;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilgarson.Common.Common;
import com.example.mobilgarson.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    EditText editPhone, editPassword;
    Button btnSignIn,btnForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editPhone = findViewById(R.id.edit_Phone);
        editPassword = findViewById(R.id.edit_Password);
        btnSignIn = findViewById(R.id.btn_signIn);
        btnForgot=findViewById(R.id.frgt_Password);

        //Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Check if user not exist in Database
                        if(dataSnapshot.child(editPhone.getText().toString()).exists()) {
                            //Get user information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class);
                            user.setPhone(editPhone.getText().toString());//set phone
                            if (user.getPassword().equals(editPassword.getText().toString())) {

                                Toast.makeText(SignIn.this, "Giriş Başarılı!!!", Toast.LENGTH_SHORT).show();
                                Intent homeIntent = new Intent(SignIn.this, IntroActivity.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();
                            } else {

                                Toast.makeText(SignIn.this, "Hatalı Şifre!!!", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "Kullanıcı bulunamadı!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, ResetPassword.class);
                startActivity(intent);
                finish();

            }
        });
    }


}
