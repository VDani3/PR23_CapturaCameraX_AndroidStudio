package com.dani.pr23_capturacamarax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.TextView;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {
    private PreviewView previewview;
    private ListenableFuture<ProcessCameraProvider> cameraProvider;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        previewview = findViewById(R.id.preview);
        cameraProvider = ProcessCameraProvider.getInstance(this);

        cameraProvider.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider1 = cameraProvider.get();

                    // Configuración de la vista previa
                    Preview preview = new Preview.Builder().build();
                    CameraSelector cameraSelector = new CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build();

                    // Vincula la vista previa al ciclo de vida de la actividad
                    preview.setSurfaceProvider(previewview.getSurfaceProvider());

                    // Configuración de la cámara con la vista previa
                    cameraProvider1.bindToLifecycle(CameraActivity.this, cameraSelector, preview);

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }
}
