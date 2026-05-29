package com.lggyx.lgdemo04;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private EditText etUid;
    private EditText etPwd;
    private Button btnReg;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("user", MODE_PRIVATE);

        if (sp.contains("uid")) {
            goHome();
            return;
        }

        setContentView(R.layout.activity_main);

        etUid = findViewById(R.id.etUid);
        etPwd = findViewById(R.id.etPwd);
        btnReg = findViewById(R.id.btnReg);
        btnLogin = findViewById(R.id.btnLogin);

        btnReg.setOnClickListener(v -> register());
        btnLogin.setOnClickListener(v -> login());
    }

    private void register() {
        String uid = etUid.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();

        if (uid.isEmpty() || pwd.isEmpty()) {
            Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        sp.edit()
                .putString("uid", uid)
                .putString("pwd", pwd)
                .apply();

        Toast.makeText(this, "Registered successfully, please login", Toast.LENGTH_SHORT).show();
        etUid.setText("");
        etPwd.setText("");
    }

    private void login() {
        String uid = etUid.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();

        if (uid.isEmpty() || pwd.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        String storedUid = sp.getString("uid", "");
        String storedPwd = sp.getString("pwd", "");

        if (!uid.equals(storedUid) || !pwd.equals(storedPwd)) {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        goHome();
    }

    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
