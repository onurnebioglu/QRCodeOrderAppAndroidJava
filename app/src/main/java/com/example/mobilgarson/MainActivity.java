package com.example.mobilgarson;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnSignUp;
    private int camera_permission=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);





        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
                { Intent signUpIntent = new Intent(MainActivity.this, PhoneActivity.class);
                    startActivity(signUpIntent);
                }else {
                    requestPhonePermission();
                }

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIntent = new  Intent(MainActivity.this, SignIn.class);
                startActivity(signIntent);
            }
        });
    }
    private void requestPhonePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE))
        {
            new AlertDialog.Builder(this)
                    .setTitle("İzin Gerekli").setMessage("Doğrulama kodu için uygulamaya izin vermelisiniz")
                    .setPositiveButton("İzin ver", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},camera_permission);


                        }
                    }).setNegativeButton("İzin Verme", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            }).create().show();

        }else{ ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},camera_permission);}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode ==camera_permission){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Intent signUpIntent = new Intent(MainActivity.this, PhoneActivity.class);
                startActivity(signUpIntent);
            }else {
                Toast.makeText(this,"İzin verilmedi",Toast.LENGTH_SHORT);
            }
        }    }

}
