package msp18034.fooddiary.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import msp18034.fooddiary.utils.Host;
import msp18034.fooddiary.utils.CaptureImagePresenter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by tom13 on 08/03/2018.
 * Background process used to send an image to a host.
 */

public class UploadImage extends AsyncTask<Void, Void, String> {

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private Host host;
    private String image;
    private String userid = "APPuser1";
    private CaptureImagePresenter captureImagePresenter;
    public final static int CONNECT_TIMEOUT =30;
    public final static int READ_TIMEOUT=10;
    public final static int WRITE_TIMEOUT=10;

    public UploadImage(Host host, String image, CaptureImagePresenter captureImagePresenter) {
        this.host = host;
        this.image = image;
        this.userid = "APPuser1";
        this.captureImagePresenter = captureImagePresenter;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        String result = "";
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                .build();
        String imageToSend = image;
        String user = userid;
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imageToSend)
                .addFormDataPart("user", user)
                .build();

        Request request = new Request.Builder().url(host.getUrl())
                .post(requestBody).build();

        Response response = null;
        try {
            Log.i("!!!","response!!!start!!!!!!!!!!");
            response = client.newCall(request).execute();
            Log.i("!!!","response!!!ok!!!!!!!!!!");
            result = response.body().string();
            response.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected void onPostExecute(String result) {
        captureImagePresenter.responseFromSever(result);
        super.onPostExecute(result);
    }
}

