package com.dani.pr23_capturacamarax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static final String[] CAMERA_PERMISSION = new String[]{android.Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    public static String photoName;
    private String photoPath = "0.jpg";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.foto);
        photoPath =  obtenerUltimaFoto();

        if (photoPath != null) {
            File foto = new File(photoPath);
            Uri uriPath = Uri.fromFile(foto);
            imageView.setImageURI(uriPath);
        }
        Button enableCamera = findViewById(R.id.enableCamera);
        enableCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasCameraPermission()) {
                    enableCamera();
                } else {
                    requestPermission();
                }
            }
        });
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }

    private String obtenerUltimaFoto() {
        File directorio = new File(getFilesDir().getAbsolutePath());
        File[] archivos = directorio.listFiles();

        if (archivos != null && archivos.length > 1) {
            Arrays.sort(archivos, Collections.reverseOrder());
            return archivos[1].getAbsolutePath();
        } else {
            File newFile = new File(directorio.getAbsolutePath()+photoName);
            newFile.mkdir();
        }

        return null;
    }

    private void enableCamera() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
}