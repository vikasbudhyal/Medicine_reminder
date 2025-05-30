package com.example.medicine_reminder;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class LoginActivity extends Activity {

    EditText emailInput, passwordInput,fullnameInput,dobInput;
    Button loginBtn;
    DBhelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fullnameInput = findViewById(R.id.fullnameInput);
        dobInput = findViewById(R.id.dobInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);
        dbHelper = new DBhelper(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = dbHelper.checkUser(email, password);
                    if (check) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                    TextView registerLink = findViewById(R.id.registerLink);
                    registerLink.setOnClickListener(v -> {
                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    });

                }
            }
        });
    }
}
