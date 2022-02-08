package com.example.mobilgarson;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class ResetPassword extends AppCompatActivity {


    private Spinner spinner;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        spinner = findViewById(R.id.spinnerCountries3);
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_dropdown_item, CountryData.countryNames));

        editText = findViewById(R.id.editTextPhone3);

        findViewById(R.id.buttonContinue3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Geçerli numara giriniz");
                    editText.requestFocus();
                    return;
                }

                String phoneNumber2 = number;

                String phoneNumber = "+" + code + number;

                Intent intent = new Intent(ResetPassword.this, VerifyPhoneActivity2.class);
                intent.putExtra("phonenumber", phoneNumber);
                intent.putExtra("phonenumber2",phoneNumber2);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}
