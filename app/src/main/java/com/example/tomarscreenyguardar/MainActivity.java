package com.example.tomarscreenyguardar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    Button btnSave;
    EditText myEdTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }



    private void init(){
        btnSave = (Button) findViewById(R.id.btnSave);
        image = (ImageView) findViewById(R.id.myImgVw);
        myEdTxt = (EditText) findViewById(R.id.myEditContent);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageOnGallery();
            }
        });
    }


    //Es el método el que no permite guardar las imágenes
    private void saveImageOnGallery() {

        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            FileOutputStream outputStream = null;
            File file = Environment.getExternalStorageDirectory();
            File dir = new File(file.getAbsolutePath() + "/MyPics");
            dir.mkdirs();

            String filename = String.format("%d.png",System.currentTimeMillis());
            File outFile = new File(dir,filename);
            try{
                outputStream = new FileOutputStream(outFile);
            }catch (Exception e){
                e.printStackTrace();
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
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            myEdTxt.setText(e.toString());
        }


    }
}