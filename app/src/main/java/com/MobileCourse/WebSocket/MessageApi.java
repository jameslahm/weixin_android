package com.MobileCourse.WebSocket;

import com.MobileCourse.Models.ApplicationMessage;
import com.MobileCourse.Models.ConfirmMessage;
import com.MobileCourse.Models.InviteInToGroupMessage;
import com.MobileCourse.Models.Message;
import com.MobileCourse.WebSocket.Request.CreateMessage;
import com.MobileCourse.WebSocket.Request.LoginMessage;
import com.tinder.scarlet.WebSocket;
import com.tinder.scarlet.ws.Receive;
import com.tinder.scarlet.ws.Send;

import org.intellij.lang.annotations.Flow;

import io.reactivex.Flowable;

public interface MessageApi {
    @Receive
    Flowable<WebSocket.Event> observeWebSocketEvent();

    @Send
    void sendLoginMessage(LoginMessage message);

    @Send
    void sendMessage(CreateMessage message);

    @Receive
    Flowable<Message> observeMessage();

    @Receive
    Flowable<InviteInToGroupMessage> observeInviteIntoGroupMessage();

    @Receive
    Flowable<ConfirmMessage> observeConfirmMessage();

    @Receive
    Flowable<ApplicationMessage> observeApplicationMessage();

    // For Test
    @Receive
    Flowable<Ping> observePing();
}
