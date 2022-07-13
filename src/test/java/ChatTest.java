import static org.junit.jupiter.api.Assertions.assertEquals;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import server.ChatServer;

import java.io.IOException;

@RunWith(VertxUnitRunner.class)

public class ChatTest {

    private Vertx vertx;

    @Before
    public void prepare(TestContext context) throws IOException {
        vertx = Vertx.vertx();
        vertx.deployVerticle(new ChatServer())
                .onSuccess(ok -> context.assertTrue(false))
                .onFailure(context::fail);
    }

    @Test
    public void smokeTest(TestContext context) {
        vertx.createHttpClient()
                .request(HttpMethod.GET, 8080, "127.0.0.1", "/chat/123")
                .compose(req -> req.send())
                .compose(res -> res.body())
                .onSuccess(body -> {
                    context.assertTrue("Hello".equals(body.toString()));
                })
                .onFailure(failure -> context.fail(failure));
    }
}
