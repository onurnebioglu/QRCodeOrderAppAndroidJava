package com.example.mobilgarson;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PhoneActivity extends AppCompatActivity {


    private Spinner spinner;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_dropdown_item, CountryData.countryNames));

        editText = findViewById(R.id.editTextPhone);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");


        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(editText.getText().toString()).exists()){
                            Toast.makeText(PhoneActivity.this, "Numara Zaten Kayıtlı", Toast.LENGTH_SHORT).show();




                        }else {
                            String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                            String number = editText.getText().toString().trim();

                            if (number.isEmpty() || number.length() < 10) {
                                editText.setError("Geçerli numara giriniz");
                                editText.requestFocus();
                                return;
                            }

                            String phoneNumber2 = number;

                            String phoneNumber = "+" + code + number;

                            Intent intent = new Intent(PhoneActivity.this, VerifyPhoneActivity.class);
                            intent.putExtra("phonenumber", phoneNumber);
                            intent.putExtra("phonenumber2",phoneNumber2);
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

    @Override
    protected void onStart() {
        super.onStart();


    }
}
