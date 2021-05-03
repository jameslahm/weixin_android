package com.MobileCourse.Api.Response;

import android.widget.ListAdapter;

import com.MobileCourse.Models.Group;
import com.MobileCourse.Models.Message;
import com.MobileCourse.Models.User;

import java.util.List;

public class TimeLineResponse extends CommonResponse {
    List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public TimeLineResponse(List<Message> messages) {
        this.messages = messages;
    }
}
