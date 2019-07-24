package msp18034.fooddiary.utils;

import java.util.List;

import msp18034.fooddiary.R;
import msp18034.fooddiary.utils.foodrecord;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class foodrecordAdaptor extends ArrayAdapter<foodrecord> {  // 适配器，泛型表示想要适配的数据类型

    private int resourceId;

    class ViewHolder{   //当布局加载过后，保存获取到的控件信息。
        ImageView lv_image;
        TextView lv_food;
        TextView lv_calorie;
    }

    public foodrecordAdaptor(Context context, int textViewResourceId,
                        List<foodrecord> objects) {             // 第一个参数是上下文环境，第二个参数是每一项的子布局，第三个参数是数据
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;          //获取子布局
    }

    @Override     //getView方法在每个子项被滚动到屏幕内的时候都会被调用，每次都将布局重新加载一边
    public View getView(int position, View convertView, ViewGroup parent) {//第一个参数表示位置，第二个参数表示缓存布局，第三个表示绑定的view对象
        View view;
        ViewHolder viewHolder;         //实例ViewHolder，当程序第一次运行，保存获取到的控件，提高效率
        if (convertView == null) {
            viewHolder=new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(//convertView为空代表布局没有被加载过，即getView方法没有被调用过，需要创建
                    resourceId, null);     // 得到子布局，非固定的，和子布局id有关
            viewHolder.lv_image = (ImageView) view.findViewById(R.id.lv_image);//获取控件,只需要调用一遍，调用过后保存在ViewHolder中
            viewHolder.lv_food = (TextView) view.findViewById(R.id.lv_foods);  //获取控件
            viewHolder.lv_calorie = (TextView) view.findViewById(R.id.lv_calories);  //获取控件
            view.setTag(viewHolder);
        }
        else {
            view=convertView;      //convertView不为空代表布局被加载过，只需要将convertView的值取出即可
            viewHolder=(ViewHolder) view.getTag();
        }

        foodrecord r = getItem(position);//实例指定位置的水果

        Bitmap bitmap = stringToBitmap(r.getImage());
        viewHolder.lv_image.setImageBitmap(bitmap);//获得指定位置水果的id
        viewHolder.lv_food.setText(r.getFoodName());    //食物名字
        viewHolder.lv_calorie.setText(r.getCalorie());    //卡路里
        return view;

    }


    public Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}




