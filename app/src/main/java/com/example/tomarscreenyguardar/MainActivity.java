package com.example.tomarscreenyguardar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

    ImageView image;
    ImageView imageDestiny;
    Button btnSave, btnTakeScreen;
    EditText myEdTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
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
                saveImageOnGallery();
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
    private void saveImageOnGallery() {

        try {
            BitmapDrawable bitmapDrawable =  (BitmapDrawable) imageDestiny.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            FileOutputStream outputStream = null;

            File file = Environment.getExternalStorageDirectory();

            File dir = new File(file, "/ScreenPictures/");

            dir.mkdirs();

            //Creas el nombre del archivo
            String filename = String.format(Locale.getDefault(),"%d.png",System.currentTimeMillis());

            //Crear ya el archivo con su dirección de salida
            File outFile = new File(dir,filename);


            try{
                //Como tal, aquí ya estás mandado el archivo
                //Aquí está el error
                outputStream = new FileOutputStream(outFile);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
               myEdTxt.setText(e.toString());
            }

            //Y aquí lo estás comprimiendo?
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            try{
                //??
                outputStream.flush();
            }catch (Exception e){
                //acá no hay error
                e.printStackTrace();
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                myEdTxt.setText(e.toString());

            }
            try{
                //Cierras la escritura, aquí, se comprende
                outputStream.close();
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                myEdTxt.setText(e.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            myEdTxt.setText(e.toString());
        }


    }

    //Tal parece que se están presentando errores en la creación del archivo o del directorio
    /*java.io.FileNotFoundException: /storage/emulated/0/MyPics/1617481829247.png: open failed: ENOENT (No such file or directory)*/

    //En otras palabras, el error se origina en la creación del archivo

    public void createDirectory(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "/LoteriaApp/");

            boolean elDirectorioEstaCreado = file.exists();
            if (!elDirectorioEstaCreado){
                file.mkdirs();
            }if (elDirectorioEstaCreado){
                //Do something
            }
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


}