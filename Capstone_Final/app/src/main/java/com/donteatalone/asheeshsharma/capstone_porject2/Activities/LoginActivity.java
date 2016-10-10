package com.donteatalone.asheeshsharma.capstone_porject2.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.donteatalone.asheeshsharma.capstone_porject2.R;
import com.donteatalone.asheeshsharma.capstone_porject2.Utilities.RequestPermissionForMarshmallow;
import com.donteatalone.asheeshsharma.capstone_porject2.Utilities.SharedPrefUtils;
import com.donteatalone.asheeshsharma.capstone_porject2.Utilities.TextUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Asheesh.Sharma on 05-10-2016.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    public String TAG = LoginActivity.this.getClass().getSimpleName();
    RequestPermissionForMarshmallow marshPermission;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        /*if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, NearbyCitiesActivity.class));
            finish();
        }*/
        setContentView(R.layout.activity_login);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        marshPermission = new RequestPermissionForMarshmallow(currentapiVersion, this);
        marshPermission.AskForLocationPermission();
        marshPermission.AskForLocationCPermission();
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        /*btnReset = (Button) findViewById(R.id.btn_reset_password);*/
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/quartzo.ttf");
        btnLogin.setTypeface(typeface);
        auth = FirebaseAuth.getInstance();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                if(!TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),getString(R.string.inv_em),Toast.LENGTH_SHORT).show();
                }
                else if(!TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),getString(R.string.inv_ps),Toast.LENGTH_SHORT).show();
                }else if(!TextUtils.isEmpty(password) && !TextUtils.isEmpty(email) ){
                    Toast.makeText(getApplicationContext(),getString(R.string.inv_c),Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password) && TextUtils.isEmpty(email)) {
                    progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (password.length() < 6) {
                                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        SharedPrefUtils.setloginSuccessfull(getApplicationContext());
                                        Intent intent = new Intent(LoginActivity.this, NearbyCitiesActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(getApplicationContext(),getString(R.string.inv_c),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
