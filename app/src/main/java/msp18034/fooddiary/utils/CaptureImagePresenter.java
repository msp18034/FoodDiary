package msp18034.fooddiary.utils;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import msp18034.fooddiary.ProcessedActivity;
import msp18034.fooddiary.R;
import msp18034.fooddiary.utils.UploadImage;
import msp18034.fooddiary.utils.HostFactory;
import msp18034.fooddiary.utils.Base64Image;
import msp18034.fooddiary.utils.ExifUtil;
import msp18034.fooddiary.CaptureImageActivity;
import msp18034.fooddiary.MainActivity;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

/**
 * Created by tom13 on 06/03/2018.
 * Presenter class to control the CaptureImageActivity View.
 */

public class CaptureImagePresenter {

    private Activity activity;
    private static final int CONTENT_REQUEST=1337;
    private File output = null;
    private Map<String, Runnable> imageCapture;
    private static final int GALLERY = 1;

    public CaptureImagePresenter(Activity activity, String medium) {
        this.activity = activity;
        this.imageCapture = new HashMap<>();
        imageCapture.put("camera", () -> takePicture());
        imageCapture.put("gallery", () -> gallery());

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        output = new File(dir, "FoodImage.jpg");

        Log.i("camera","start!!!!!!!!!!!!!!!!!!");
        //TODO: 打开摄像头还有问题

        imageCapture.get(medium).run();
        dir.delete();
        Log.i("camera","dir !!!delete!!!!!!!!!!!!!!!!!!");
    }

    private void gallery() {
        Intent i =new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        Log.i("camera","gallery!!!!!!!!!!!!!!!!!!!!!!!!");

        activity.startActivityForResult(i, GALLERY);
    }

    private void takePicture() {
        Intent i =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        Log.i("camera","camera!!!!!!!!!!!!!!!!!!!!!!!!");
        activity.startActivityForResult(i, CONTENT_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ImageView imageView= activity.findViewById(R.id.imageView);
        Bitmap bitmap = null;
        Bitmap orientedBitmap;

        if (EasyPermissions.hasPermissions(activity, galleryPermissions)) { }
        else {
            EasyPermissions.requestPermissions(activity, "Access for storage",
                    101, galleryPermissions);
        }

        if (requestCode == GALLERY && resultCode != 0) {
            Uri mImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), mImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Bitmap.createScaledBitmap(bitmap, 300, 400, false);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(output);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        if (requestCode == CONTENT_REQUEST || requestCode == GALLERY  && resultCode == RESULT_OK ) {
            bitmap = BitmapFactory.decodeFile(output.getAbsolutePath());
            orientedBitmap = ExifUtil.rotateBitmap(output.getAbsolutePath(), bitmap);
            Bitmap.createScaledBitmap(orientedBitmap, 300, 400, false);
            imageView.setImageBitmap(orientedBitmap);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    public void cancel() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public void sendImage() {
        UploadImage uploadImage = new UploadImage(new HostFactory().createHost(), new Base64Image(output).getBase64Image(), this);
        uploadImage.execute();
    }

    public void responseFromSever(String response) {
        String user = "", image = "";
        String msg_f = "Food";
        String msg_c = "Calorie(cal)";
        int total = 0;
        Log.i("response",response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            user = jsonObject.getString("user");
            image = jsonObject.getString("drawn_img");
            JSONArray classes = jsonObject.getJSONArray("class");
            JSONArray calories = jsonObject.getJSONArray("calories");
            double processtime = jsonObject.getDouble("process_time");

            for (int i=0; i<calories.length(); i++){
                int cal = (int)calories.getDouble(i);
                msg_f += "\n" + classes.getString(i);
                msg_c += "\n" + cal;
                total += cal;
            }
            msg_f += "\nTotal";
            msg_c += "\n"+total;

        }catch (JSONException e) {
            e.printStackTrace();
        }
        updateUI(image, msg_f, msg_c, total);
    }


    public void updateUI(String img, String msg_f, String msg_c, int total) {
        //将返回的数据用processedActivity 输出
        Intent intent = new Intent(activity, ProcessedActivity.class);
        intent.putExtra("img", img);
        intent.putExtra("msg_f", msg_f);
        intent.putExtra("msg_c", msg_c);
        intent.putExtra("total", total);
        activity.startActivity(intent);
    }


    public Activity getActivity(){
        return activity;
    }

}
