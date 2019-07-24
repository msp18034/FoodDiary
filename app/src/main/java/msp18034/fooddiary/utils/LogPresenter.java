package msp18034.fooddiary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import msp18034.fooddiary.FoodLogsActivity;
import msp18034.fooddiary.MainActivity;
import msp18034.fooddiary.ProcessedActivity;
import msp18034.fooddiary.R;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


public class LogPresenter {

    private Activity activity;
    private File output = null;
    private String userid;


    public LogPresenter(Activity activity, String userid) {
        this.activity = activity;
        this.userid = userid;
    }


    public void sendQuery() {
        QuerytoCassandra qtc = new QuerytoCassandra(new HostFactory().createHost(11451),userid,this);
        qtc.execute();
    }

    public void responseFromSever(String response) {
        //TODO:来自服务器的回复！！！！！！这里要改
        //{"id": "test", "time": "1970-01-19 02:23:00.741Z", "calorie": [1.23, 45.23], "carbo": null,
        //  "fat": null, "fiber": null, "food": ["food1", "food2"], "photo": null, "protein": null}


        String[] result = response.split("\\|");
        String[] imgs = new String[result.length];
        String[] foods_text = new String[result.length];
        String[] calories_text = new String[result.length];
        int total_cal = 0;
        double total_carbo = 0, total_fat = 0;
        double total_fiber = 0, total_protein = 0;


        try {
            for (int i=0; i< result.length; i++){
                JSONObject jsonObject = new JSONObject(result[i]);
                imgs[i] = jsonObject.getString("drawn_img");

                JSONArray classes = jsonObject.getJSONArray("class");
                JSONArray calorie = jsonObject.getJSONArray("calories");
                JSONArray carbo = jsonObject.getJSONArray("carbo");
                JSONArray fat = jsonObject.getJSONArray("fat");
                JSONArray fiber = jsonObject.getJSONArray("fiber");
                JSONArray protein = jsonObject.getJSONArray("protein");
                String food_t = "";
                String cal_t = "";
                for (int j=0; j<classes.length(); j++){
                    food_t += classes.getString(j)+"\n";
                    int cal = (int)calorie.getDouble(j);
                    cal_t += cal + "\n";
                    total_cal += cal;
                    total_carbo += carbo.getDouble(j);
                    total_fat += fat.getDouble(j);
                    total_fiber += fiber.getDouble(j);
                    total_protein += protein.getDouble(j);
                }
                foods_text[i] = food_t;
                calories_text[i] = cal_t;

            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        //updateUI(imgs, foods_text, calories_text, total_cal, total_carbo, total_fat,total_fiber,total_protein);
        FoodLogsActivity foodlogsActivity = (FoodLogsActivity) activity;
        foodlogsActivity.onResponse(imgs, foods_text, calories_text, total_cal, total_carbo, total_fat,total_fiber,total_protein);

    }


    public void updateUI(String img, String msg_f, String msg_c, int total) {
        //将返回的数据用processedActivity 输出
        FoodLogsActivity foodlogsActivity = (FoodLogsActivity) activity;
        //foodlogsActivity.onResponse(top1_prediction, calories);
    }


    public Activity getActivity(){
        return activity;
    }

}
