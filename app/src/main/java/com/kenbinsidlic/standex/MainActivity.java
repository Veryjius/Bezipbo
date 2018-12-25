package com.kenbinsidlic.standex;

import java.io.InputStream;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private TextView tv_describe, tv_stand, tv_master;
    private Spinner stand_list = null, master_list = null;
    private AssetManager assets, img_assets;
    private XmlPullParser parser;
    private ArrayAdapter<String> adapter;
    private List<String> list;
    private Button bt;
    private String describe;
    private String sInfo, mInfo;
    private String imagepath;
    private String type;
    private String master;
    private InputStream img = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.imageView);
        tv_describe = (TextView) findViewById(R.id.textView4);
        tv_stand = (TextView) findViewById(R.id.standname);
        tv_master = (TextView) findViewById(R.id.textView3);
        stand_list = (Spinner) super.findViewById(R.id.stand_list);
        //master_list = (Spinner) super.findViewById(R.id.master_list);
        bt = (Button) findViewById(R.id.button);
        //et_st_name = (EditText)findViewById(R.id.standnameedit);
        //et_st_master = (EditText)findViewById(R.id.masternameedit);
        assets = this.getAssets();
        img_assets = this.getAssets();
        stand_list.setOnItemSelectedListener(new ProvOnStandSelectedListener());
        //master_list.setOnItemSelectedListener(new ProvOnMasterSelectedListener());


        bt.setOnClickListener(new View.OnClickListener() {
            StringBuilder newstringBuilder = new StringBuilder();

            @Override
            public void onClick(View v) {
                String content = "";
                InputStream imgStream = null;
                try {
                    InputStream inputStream = getResources().getAssets().open("standata.json");
                    content = GsonTool.readStream(inputStream);
                    describe = GsonTool.readGsonString(content, sInfo, "stand", "describe");//获得描述
                    type = GsonTool.readGsonString(content, sInfo, "stand", "type");//获得描述
                    imagepath = "image/" + GsonTool.readGsonString(content, sInfo, "stand", "image") + ".png";//获得图片
                    master = GsonTool.readGsonString(content, sInfo, "stand", "master");//获得替身使者
                    imgStream = getAssets().open(imagepath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeStream(imgStream, null, options);
                image.setImageBitmap(bitmap);
                tv_describe.setText("【" + sInfo + "】\n\n" + "[" + type + "]\n\n" + describe);
                tv_master.setTextColor(Color.parseColor("#000000"));
                tv_master.setText(master);

            }
        });
    }

    //OnItemSelected监听器
    private class ProvOnStandSelectedListener implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
            //获取选择的项的值
            sInfo = adapter.getItemAtPosition(position).toString();
            //Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            String sInfo = "什么也没选！";
            Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();

        }
    }
}




