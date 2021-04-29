package com.MobileCourse.WebSocket;

import com.MobileCourse.Models.ApplicationMessage;
import com.MobileCourse.Models.InviteInToGroupMessage;
import com.MobileCourse.Models.Message;
import com.tinder.scarlet.WebSocket;
import com.tinder.scarlet.ws.Receive;
import com.tinder.scarlet.ws.Send;

import org.intellij.lang.annotations.Flow;

import io.reactivex.Flowable;

public interface MessageApi {
    @Receive
    Flowable<WebSocket.Event> observeWebSocketEvent();

    @Send
    void sendMessage(Message message);

    @Receive
    Flowable<Message> observeMessage();

    @Receive
    Flowable<InviteInToGroupMessage> observeInviteIntoGroupMessage();

    @Receive
    Flowable<ApplicationMessage> observeApplicationMessage();
}
