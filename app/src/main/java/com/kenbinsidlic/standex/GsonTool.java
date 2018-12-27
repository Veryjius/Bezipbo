package com.kenbinsidlic.standex;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by 尽途 on 2017/05/26.
 * * Editted by Kenbinsidlic on 2018/11/25.
 */

public class GsonTool {
    /**
     * 将json格式转化为字符串格式并返回
     * @param is
     * @return
     * @throws Exception
     */
    public static String readStream(InputStream is) throws Exception{
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int len=0;
        while ((len=is.read(buffer))!=-1){
            byteArrayOutputStream.write(buffer,0,len);
        }
        is.close();
        byteArrayOutputStream.close();
        String content=new String(byteArrayOutputStream.toByteArray());
        return content;
    }
    /**
     * 通过输入gson字符串和键值、目标值来从输入gson字符串中寻找需要的值
     */
    public static String readGsonString(String string,String sInfo,String key,String obj){
        String str=null;
        try {
            JSONObject jsonObject=new JSONObject(string);
            JSONArray jsonArray=jsonObject.getJSONArray(key);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                //str = object.getString(obj);
                if (object.getString("zhname").equals(sInfo)) {
                    str = object.getString(obj);
                    break;
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return str;
    }
}

