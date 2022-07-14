import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import server.ChatServer;

@ExtendWith(VertxExtension.class)
public class ChatTest {

    Vertx vertx = Vertx.vertx();

    @BeforeEach
    void prepare(Vertx vertx, VertxTestContext testContext) {
        // Deploy the verticle
        vertx.deployVerticle(new ChatServer())
                .onSuccess(ok -> testContext.completeNow())
                .onFailure(failure -> testContext.failNow(failure));
    }

    @Test
    @DisplayName("Smoke test: check that the HTTP server responds")
    void smokeTest(Vertx vertx, VertxTestContext testContext) {
        // Issue an HTTP request
        vertx.createHttpClient()
                .request(HttpMethod.POST, 8080, "127.0.0.1", "/v1/sendMessage")
                .compose(request -> request.send())
                .compose(response -> response.body())
                .onSuccess(body -> testContext.verify(() -> {
                    // Check the response
                    assertEquals("Greetings!", body.toString());
                    testContext.completeNow();
                }))
                .onFailure(failure -> testContext.failNow(failure));
    }

    @Test
    public void smokeTest(VertxTestContext context) {
        vertx.createHttpClient()
                .request(HttpMethod.POST, 8080, "127.0.0.1", "/chat/123")
                .compose(req -> req.send())
                .compose(res -> res.body())
                .onSuccess(body -> {
                    System.out.println("onSuccess:" + body);
                    context.completeNow();
                })
                .onFailure(failure -> {
                        System.out.println("Failure:" + failure);
                        context.failNow(failure);
                });
    }
}
