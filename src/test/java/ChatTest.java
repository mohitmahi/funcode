import app.GenericCodec;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.impl.Http2ServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import model.ChatMessage;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import server.ChatServer;

public class ChatTest {
    @Mock
    RoutingContext mock;

    Vertx vertx = Vertx.vertx();

    @Before
    public void setup() {
        Mockito.when(mock.getBodyAsString()).thenReturn(Json.encode(ChatMessage.builder().from("Test").build()));
    }
    @Test
    public void smokeTest1() throws Exception {

        vertx.eventBus().registerDefaultCodec(ChatMessage.class, new GenericCodec<>(ChatMessage.class));
        ChatServer server = new ChatServer(vertx);
        server.start();

        Mockito.when(mock.getBodyAsString()).thenReturn(Json.encode(ChatMessage.builder().from("Test").build()));
        vertx.eventBus().<ChatMessage>consumer("Message-Bus1")
                .handler(getEvents());

        server.postToEventBus(mock);

    }
    private Handler<Message<ChatMessage>> getEvents() {
        Handler<Message<ChatMessage>> messageHandler = msg -> {
            System.out.println("Received Message: " + msg.body().toString());
            msg.reply("Accepted");
        };
        return messageHandler;
    }
}
