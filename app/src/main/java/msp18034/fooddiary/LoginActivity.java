package msp18034.fooddiary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;


public class LoginActivity extends AppCompatActivity {

    private EditText userid_l,password_l,height_l,weight_l,age_l;
    private Button update;
    private RadioGroup sexgroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("id", null);
        String password = sharedPreferences.getString("password", null);
        float height = sharedPreferences.getFloat("height",1.7f);
        float weight = sharedPreferences.getFloat("weight",60f);
        int age = sharedPreferences.getInt("age",20);
        boolean sex = sharedPreferences.getBoolean("sex",true);
        //sex false: male, true: female

        userid_l = (EditText) findViewById(R.id.userid);
        password_l = (EditText) findViewById(R.id.password);
        height_l = findViewById(R.id.height);
        weight_l = findViewById(R.id.weight);
        age_l = findViewById(R.id.age);
        sexgroup = findViewById(R.id.sexgroup);
        update = findViewById(R.id.update);

        userid_l.setText(userid);
        password_l.setText(password);

        height_l.setText(String.valueOf(height));
        weight_l.setText(String.valueOf(weight));
        age_l.setText(String.valueOf(age));
        if (sex) sexgroup.check(R.id.femaleButton);
        else sexgroup.check(R.id.maleButton);


        update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = userid_l.getText().toString();
                String password = password_l.getText().toString();
                float height = Float.parseFloat(height_l.getText().toString());
                float weight = Float.parseFloat(weight_l.getText().toString());
                int age = Integer.parseInt(age_l.getText().toString());
                int calorie;
                int checkid = sexgroup.getCheckedRadioButtonId();
                boolean sex;
                if (checkid == R.id.femaleButton) {
                    sex = true; //性别女
                    calorie = (int) ((655 + 9.6*weight + 190*height - 4.7*age) * 1.1);
                }
                else {
                    sex = false;  //性别男
                    calorie = (int) ((66 + 13.8*weight + 500*height - 6.8*age) * 1.2);
                }

                Log.i("daily calorie",String.valueOf(calorie));
//                男：[66 + 1.38 x 体重(kg) + 5 x 高度(cm) - 6.8 x 年龄] x 活动量
//                女：[65.5 + 9.6 x 体重(kg) + 1.9 x 高度(cm) - 4.7 x 年龄] x 活动量
//                一般人的活动量由1.1 - 1.3不等，活动量高数值便愈高，甚至有可能高出1.3的数值，
//                若平日只坐在办公室工作的女性，活动量约1.1，运动量高的人约为1.3。
//                例如：身高156cm，体重46kg的18岁女性，每日所需的卡路里为1580Kca|

                SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id",userid);
                editor.putString("password",password);
                editor.putFloat("height",height);
                editor.putFloat("weight",weight);
                editor.putInt("age",age);
                editor.putInt("calorie",calorie);
                editor.putBoolean("sex",sex);
                editor.commit();

                Intent myIntent = new Intent(view.getContext(),
                        MainActivity.class);
                startActivity(myIntent);

            }
        });

    }

}

