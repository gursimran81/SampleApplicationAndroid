package com.example.gursimransingh.contentproviderdemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserSignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    @BindView(R.id.editTextName)
    EditText editTextName;

    @BindView(R.id.editTextPassword)
    EditText editTextPassword;

    @BindView(R.id.editTextEmail)
    EditText editTextEmail;

    @BindView(R.id.radioButtonMale)
    RadioButton radioButtonMale;

    @BindView(R.id.radioButtonFemale)
    RadioButton radioButtonFemale;

    @BindView(R.id.spinner)
    Spinner spinnerCity;

    @BindView(R.id.button)
    Button buttonSignUp;

    ArrayAdapter<String> adapter;
    ContentResolver contentResolver;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        ButterKnife.bind(this);
        contentResolver = getContentResolver();
        user = new User();

        buttonSignUp.setOnClickListener(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Select City");//0
        adapter.add("Ludhiana");
        adapter.add("Chandigarh");
        adapter.add("Jalandhar");
        adapter.add("Pune");

        spinnerCity.setAdapter(adapter);
        spinnerCity.setOnItemSelectedListener(this);
        radioButtonMale.setOnClickListener(this);
        radioButtonFemale.setOnClickListener(this);

    }

//    @OnClick(R.id.button)
//    public void SignUp(){
//        Toast.makeText(UserSignUpActivity.this,"Hey",Toast.LENGTH_LONG).show();
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String city = adapter.getItem(i);
        user.setCity(city);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button:
                user.setName(editTextName.getText().toString().trim());
                user.setEmail(editTextEmail.getText().toString().trim());
                user.setPassword(editTextPassword.getText().toString().trim());

                insertUser();
                clear();
                break;
            case R.id.radioButtonMale:
                user.setGender("Male");
                break;
            case R.id.radioButtonFemale:
                user.setGender("Female");
                break;
        }
    }

    void insertUser() {
        ContentValues values = new ContentValues();
        values.put(Util.COL_NAME, user.getName());
        values.put(Util.COL_EMAIL, user.getEmail());
        values.put(Util.COL_PASSWORD, user.getPassword());
        values.put(Util.COL_GENDER, user.getGender());
        values.put(Util.COL_CITY, user.getCity());

        Uri uri = contentResolver.insert(Util.USER_URI, values); //Call insert of Content Provider
        Toast.makeText(this, user.getName() + " registered successfully with id " + uri.getLastPathSegment(), Toast.LENGTH_LONG).show();
    }

    void clear() {
        editTextName.setText("");
        editTextPassword.setText("");
        editTextEmail.setText("");
        spinnerCity.setSelection(0);
        radioButtonFemale.setChecked(false);
        radioButtonMale.setChecked(false);
    }

}
