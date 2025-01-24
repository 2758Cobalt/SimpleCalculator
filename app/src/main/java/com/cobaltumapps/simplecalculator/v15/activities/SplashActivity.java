package com.cobaltumapps.simplecalculator.v15.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cobaltumapps.simplecalculator.v15.google.admob.SimpleApplication;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivityLog";
    private static final long SPLASH_TIMEOUT = 3000;

    private boolean isAdShown = false;
    private boolean isMainActivityStarted = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Загружаем рекламу и задаём колбэк для её показа
        SimpleApplication app = (SimpleApplication) getApplication();
        app.showAdIfAvailable(this, this::onAdComplete);

        // Устанавливаем таймер для перехода к MainActivity через 5 секунд
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!isAdShown) {
                Log.d(TAG, "Ad not shown within timeout, launching MainActivity.");
                startMainActivity();
            }
        }, SPLASH_TIMEOUT);
    }

    /**
     * Колбэк, вызываемый после завершения показа рекламы
     */
    private void onAdComplete() {
        isAdShown = true; // Устанавливаем флаг, что реклама была показана
        if (!isMainActivityStarted) {
            startMainActivity();
        }
    }

    /**
     * Запускаем MainActivity
     */
    private void startMainActivity() {
        if (isMainActivityStarted) return;

        isMainActivityStarted = true; // Устанавливаем флаг, что MainActivity запущена
        Intent intent = new Intent(SplashActivity.this, MainCalculatorActivity.class);
        startActivity(intent);
        finish(); // Завершаем SplashActivity
    }
}
