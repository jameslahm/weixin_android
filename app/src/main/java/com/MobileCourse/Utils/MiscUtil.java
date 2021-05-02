package com.MobileCourse.Utils;

import android.util.Log;
import android.widget.ImageView;

import com.MobileCourse.Models.Message;
import com.MobileCourse.R;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MiscUtil {
    public static Long getCurrentTimestamp(){
        return System.currentTimeMillis();
    }

    public static String formatTimestamp(long timestamp){
        String pattern = "yyyy/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(timestamp));
    }

    public static boolean checkSingleOrGroupMessage(Message message){
        if(message.getFrom()!=null && (message.getMessageType().equals(Constants.MessageType.SINGLE) ||
                message.getMessageType().equals(Constants.MessageType.GROUP))){
            return true;
        }
        return false;
    }

    public static void loadImage(ImageView imageView, String url){
        Log.e("LOADIMAGE",String.valueOf(url));
        if(url==null){
            url = "http://139.196.81.14:7998/upload/avatar1.jpeg";
        }
        Glide.with(imageView.getContext()).load(url).placeholder(R.drawable.avatar2).into(imageView);
    }
}
