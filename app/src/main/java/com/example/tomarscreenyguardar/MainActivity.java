package com.example.tomarscreenyguardar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Locale;

import static com.example.tomarscreenyguardar.TomarScreenshot.tomarCaptura;

public class MainActivity extends AppCompatActivity {

    //Problemas con la creación de un fichero
    private static final int REQUEST_CODE_ASK_PERMISSION = 111;
    ImageView image;
    ImageView imageDestiny;
    Button btnSave, btnTakeScreen;
    EditText myEdTxt;

    private final String dirName = "AppSS";
    private File fileDir = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        callPermission();
        fileDir = creaetDirectory(dirName);

    }


    private void init() {
        btnSave = (Button) findViewById(R.id.btnSave);
        image = (ImageView) findViewById(R.id.myImgVw);
        myEdTxt = (EditText) findViewById(R.id.myEditContent);
        btnTakeScreen = (Button) findViewById(R.id.btnTake);
        imageDestiny = (ImageView) findViewById(R.id.scndImageView);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageOnGallery(fileDir);
                Toast.makeText(MainActivity.this, "Imagen guardada", Toast.LENGTH_SHORT).show();
            }
        });
        btnTakeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = tomarCaptura(image);
                imageDestiny.setImageBitmap(b);
            }
        });

    }

    //Es el método el que no permite guardar las imágenes
    private void saveImageOnGallery(File dir) {

        try {
            BitmapDrawable bitmapDrawable =  (BitmapDrawable) imageDestiny.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            FileOutputStream outputStream = null;

            String filename = String.format("%d.png",System.currentTimeMillis());

            File outFile = new File(dir,filename);


            try{

                outputStream = new FileOutputStream(outFile);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                myEdTxt.setText(e.toString());
            }

            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            try{
                outputStream.flush();
            }catch (Exception e){
                e.printStackTrace();

            }
            try{
                outputStream.close();
            }
            catch (Exception e){
                e.printStackTrace();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private File creaetDirectory(String dir) {
        File file = new File(getExternalFilesDir(null),dir);

        if (!file.exists()){

            file.mkdir();

            Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_SHORT).show();
        }else
        {

            Toast.makeText(MainActivity.this,"Folder Already Exists",Toast.LENGTH_SHORT).show();


        }
        return file;
    }


    private void callPermission() {
        int permisosDeLecturaDeAlmacenamiento = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisosDeEscrituraDeAlmacenamiento = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permisosDeLecturaDeAlmacenamiento != PackageManager.PERMISSION_GRANTED || permisosDeEscrituraDeAlmacenamiento != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSION);
            }
        }

    }


}