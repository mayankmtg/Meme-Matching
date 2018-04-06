package com.maymayme.maymayme;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Save {

    private Context ctx2;
    private String folderName = "/MayMayMe";
    private String fileName = "test";

    public void saveImage(Context context, Bitmap imgToSave){

        ctx2 = context;

        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+folderName;
        String currentTime = getCurrentTimeAndDate();

        File dir = new File(filePath);

        if(!dir.exists()){
            dir.mkdirs();
        }

        File file = new File(dir, fileName+currentTime+".jpg");

        try{
            FileOutputStream fOut = new FileOutputStream(file);
            imgToSave.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private  void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(ctx2,
                new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String s, Uri uri) {
                        Log.e("ES","Scanned"+s+":");
                        Log.e("ES","-> Uri="+uri);
                    }
                }
        );
    }

    private String getCurrentTimeAndDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    private void UnableToSave(){
        Toast.makeText(ctx2,"Not Saved",Toast.LENGTH_SHORT).show();
    }


    private void AbleToSave(){
        Toast.makeText(ctx2,"Saved",Toast.LENGTH_SHORT).show();
    }


}
