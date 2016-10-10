package com.donteatalone.asheeshsharma.capstone_porject2.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.donteatalone.asheeshsharma.capstone_porject2.R;
import com.donteatalone.asheeshsharma.capstone_porject2.Utilities.SharedPrefUtils;
import com.donteatalone.asheeshsharma.capstone_porject2.Utilities.TextUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

/**
 * Created by Asheesh.Sharma on 05-10-2016.
 */
public class SignUpActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedPrefUtils.getSplashShown(getApplicationContext())!=0 && SharedPrefUtils.getloginSuccessfull(getApplicationContext())!=0){
            Intent intent = new Intent(SignUpActivity.this, NearbyCitiesActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        /*btnResetPassword = (Button) findViewById(R.id.btn_reset_password);*/
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/quartzo.ttf");
        btnSignUp.setTypeface(typeface);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),getString(R.string.inv_em), Toast.LENGTH_SHORT).show();
                }
                else if(!TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),getString(R.string.inv_ps),Toast.LENGTH_SHORT).show();
                }else if(!TextUtils.isEmpty(password) && !TextUtils.isEmpty(email) ){
                    Toast.makeText(getApplicationContext(),getString(R.string.inv_c),Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password) && TextUtils.isEmpty(email)){
                    progressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(SignUpActivity.this, " UserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
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
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
