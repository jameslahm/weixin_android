package com.MobileCourse.Utils;

import com.MobileCourse.Models.Message;

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
        if(message.getFrom()!=null && (message.getMessageType()== Constants.MessageType.SINGLE ||
                message.getMessageType()==Constants.MessageType.GROUP)){
            return true;
        }
        return false;
    }
}
