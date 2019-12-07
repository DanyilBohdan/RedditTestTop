package com.example.topredditapp.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.topredditapp.Image;
import com.example.topredditapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageView imBtn;
    private final int READ_EXTERNAL_STORAGE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.image_view);
        Picasso.get()
               .load(Image.im.getDat().getThumbnail())
               .placeholder(R.drawable.placeholder)
               .error(R.drawable.placeholder)
               .into(imageView);

        imBtn = findViewById(R.id.im_btn);
        imBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission();
                Toast.makeText(getApplicationContext(), SavePicture(imageView) , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String SavePicture(ImageView iv)
    {
        OutputStream fOut = null;
        try {
            File dest = new File(getGalleryPath()+"Reddit/");
            if(!dest.exists()){
                dest.mkdirs();
            }
            File file = new File(dest, System.currentTimeMillis() / 1000 + ".jpg"); // создать уникальное имя для файла основываясь на дате сохранения
            fOut = new FileOutputStream(file);


            BitmapDrawable draw = (BitmapDrawable) iv.getDrawable();
            Bitmap b = draw.getBitmap();
            b.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // сохранять картинку в jpeg-формате с 85% сжатия.
            fOut.close();
            MediaScannerConnection.scanFile(getApplicationContext(),
                    new String[]{file.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName()); // регистрация в фотоальбоме
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error " + e.getMessage() , Toast.LENGTH_SHORT).show();
            return e.getMessage();
        }
        return "Saved in Gallery";
    }

    private static String getGalleryPath() {
        return  Environment.getExternalStorageDirectory() + "/";
    }

    //Проверака на разрешение к памяти телефона
    public void permission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(ImageActivity.this, "You have already granted this permission!", Toast.LENGTH_SHORT).show();
        }
        else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("This permission is required to save the image to the gallery")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(ImageActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permissions GRANTED" , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permissions DENIED" , Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
