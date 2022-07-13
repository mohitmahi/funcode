package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import model.ChatMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageProcessor extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus().<ChatMessage>consumer("Message-Bus")
                .handler(getEvents());
    }

    private Handler<Message<ChatMessage>> getEvents() {
        Handler<Message<ChatMessage>> messageHandler = msg -> {
            System.out.println("Received Message: " + msg.body().toString());
            msg.reply("Accepted");
        };
        return messageHandler;
    }
}
