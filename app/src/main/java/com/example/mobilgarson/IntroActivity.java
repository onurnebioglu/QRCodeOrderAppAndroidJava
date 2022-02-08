package com.example.mobilgarson;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.mobilgarson.Common.Common;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

//Qr code için kullanılan kütüphane ZXing
public class IntroActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    public final static String QRCODE = "QRCODE";
    private ZXingScannerView mScannerView;

    private int phone_permission=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Tam ekran için ekranı boşaltma
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
    }

    //Kamera başlatmak için
    public void scanCode(View v) {

        if (ContextCompat.checkSelfPermission(IntroActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
        {mScannerView = new ZXingScannerView(this);
            setContentView(mScannerView);
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();

        }else {
            requestCameraPermission();
        }

    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA))
        {
            new AlertDialog.Builder(this)
                    .setTitle("İzin Gerekli").setMessage("Kamera için uygulamaya izin vermelisiniz")
                    .setPositiveButton("İzin ver", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(IntroActivity.this,new String[]{Manifest.permission.CAMERA},phone_permission);


                        }
                    }).setNegativeButton("İzin Verme", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            }).create().show();

        }else{ ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},phone_permission);}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode ==phone_permission){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                mScannerView = new ZXingScannerView(this);
                setContentView(mScannerView);
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            }else {
                Toast.makeText(this,"İzin verilmedi",Toast.LENGTH_SHORT);
            }
        }
    }

    //Kamera durdurmak için
    @Override
    protected void onPause() {
        super.onPause();
        //mScannerView.stopCamera();
    }

    //Qr code okunduğu zaman Menü ekranına geçiş yap
    @Override
    public void handleResult(Result result) {

       //qr code işlemi için try catch

        try {
            //Birden fazla restaurantı aynı veritabanına kaydetmek için
           /* String qr=result.getText().toString();
            int index=qr.indexOf(' ');
            String cafeId=qr.substring(0,index);
            Common.currentUser.setCafeId(cafeId);
            String cafeNo=qr.substring(index,qr.length());
            int tableNumber=Integer.parseInt(cafeNo.trim());
            Common.currentUser.setTable(tableNumber);*/

           //Uygulamayı tek restoranda kullanmak için
           int tableNumber = Integer.valueOf(result.getText());
           Common.currentUser.setTable(tableNumber);

            Intent intent1=new Intent(this,Home.class);
            startActivity(intent1);
            finish();

        }catch (Exception e) {
            //Hatalı qr code okutulursa verilecek uyarı
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Tarama Sonucu");
            builder1.setMessage("Lütfen masa üzerindeki kodu okutunuz.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mScannerView.resumeCameraPreview(IntroActivity.this);
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }
}
