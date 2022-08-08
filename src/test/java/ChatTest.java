import app.GenericCodec;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import model.ChatMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import server.ChatServer;

@ExtendWith(MockitoExtension.class)
@ExtendWith({VertxExtension.class
})
public class ChatTest {

    @Test
    @Order(1)
    void start_server(Vertx vertx, VertxTestContext testContext) {
        vertx.eventBus().registerDefaultCodec(ChatMessage.class, new GenericCodec<>(ChatMessage.class));
        vertx.deployVerticle(ChatServer.class.getName(), res -> { if(res.succeeded()) {
            System.out.println("Deployed a new serverVerticle" + res.result());
        } else {
            System.out.println("Failed serverVerticle" + res.result());
        }
        });

        WebClientOptions options = new WebClientOptions()
                .setUserAgent("My-App/1.2.3");
        options.setKeepAlive(false);
        WebClient webClient = WebClient.wrap(vertx.createHttpClient());
        System.out.println("Calling now");

        ChatMessage input  = ChatMessage.builder().message("Yp1").from("My1").build();
        webClient.post(8080, "localhost", "/v1/pay")
                .sendJsonObject(new JsonObject(Json.encode(input)),
                        testContext.succeeding(response -> {
                            testContext.verify(() ->
                                    Assertions.assertAll(
                                            () -> Assertions.assertEquals(200, response.statusCode()),
                                            () -> Assertions.assertEquals("OKK", response.statusMessage())
                                    )
                            );
                            testContext.completeNow();
                        })
                );
    }
}
