package br.com.spm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import br.com.spm.R;

public class Splash extends AppCompatActivity{
    private final static int DELAY = 2500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLogin();
            }
        }, DELAY);
    }

//    private void showHome() {
//        startActivity(new Intent(this, HomeActivity.class));
//        finish();
//    }

    private void showLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
