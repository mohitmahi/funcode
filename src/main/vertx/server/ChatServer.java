package server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.Json;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.NoArgsConstructor;
import model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@NoArgsConstructor
public class ChatServer extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    private final Pattern chatURL = Pattern.compile("/chat/(\\w+)");

    public ChatServer(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void start() throws Exception {
        super.start();
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.post("/v1/pay")
                .consumes("application/json")
                .handler(this::postToEventBus);

        HttpServer httpServer = vertx.createHttpServer()
                // Handle every request using the router
                .requestHandler(router)
                .exceptionHandler(exc -> {
                    logger.error("Vertx got exception" + exc);
                })
                .connectionHandler(HttpConnection -> {
                    logger.info("Vertx got a new connection" + HttpConnection);
                })
                .webSocketHandler(this::handleWebSocketEvent);

        httpServer.listen(8080);
        deployVerticle();
    }

    private void handleWebSocketEvent(ServerWebSocket event) {
        final Matcher m = chatURL.matcher(event.path());
        if (!m.matches()) return;

        final String chatRoom = m.group(1);
        final String id = event.textHandlerID();
        LocalMap<String, String> chatMap = vertx.sharedData().getLocalMap("ChatRoom");
        LocalMap<String, Integer> chatMapSize = vertx.sharedData().getLocalMap("ChatRoomSize");
        if(!chatMap.containsKey(chatRoom)) {
            chatMap.put(chatRoom, id);
            chatMapSize.put(chatRoom, 1);
        } else {
            chatMap.put(chatRoom, chatMap.get(chatRoom) + "," + id);
            chatMapSize.put(chatRoom, chatMapSize.get(chatRoom) + 1);
        }
        logger.info("Adding new connection with ID:" + id + " with chat-room:: " + chatRoom + " size: " + chatMapSize.get(chatRoom));
        event.writeTextMessage("You are now active in chatroom:" + chatRoom + ", as: " + chatMapSize.get(chatRoom) + " member");
        event.closeHandler(closeEvent -> {
            chatMapSize.put(chatRoom, chatMapSize.get(chatRoom) - 1);
        });

        handleChatEvent(event, chatRoom, id);
    }

    private void handleChatEvent(ServerWebSocket event, String chatRoom, String id) {
        event.handler(data -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
               // JsonNode rootNode = mapper.readTree(data.toString());
               // ((ObjectNode) rootNode).put("received", new Date().toString());
              //  String jsonOutput = mapper.writeValueAsString(rootNode);
               // logger.info("Json Generated:: " + jsonOutput);
                final String output = data.toString() + "\n Received: " + new Date().toString() + "\n";
                final String output1 = "\n Sent: " + new Date().toString() + "\n";

                LocalMap<String, String> chatMap2 = vertx.sharedData().getLocalMap("ChatRoom");
                List<String> userList = List.of(chatMap2.get(chatRoom).split(","));
                userList.forEach((value) -> {
                    if(!value.equals(id)) {
                        getEventBus().send(value, output);
                    } else {
                        getEventBus().send(value, output1);
                    }
                });
            } catch (Exception e) {
                logger.info("Exception:: " ,e);
                event.reject();
            }
        });
    }

    private void deployVerticle() {
        vertx.deployVerticle(ChatMessageProcessor::new,
                new DeploymentOptions()
                        .setWorker(true)
                        .setWorkerPoolName("Chat-Message")
                        .setInstances(2)
                        .setWorkerPoolSize(20),
                res -> {
                    System.out.println("Deployed ChatMessageProcessor-Verticle" + res.result());
                });
    }

    //curl http://127.0.0.1:8080/v1/pay --data '{"from":"Mohit","message":"Hello"}' -H "Content-Type: application/json"

    public void postToEventBus(RoutingContext routingContext) {
        final ChatMessage chatMessage = Json.decodeValue(routingContext.getBodyAsString(), ChatMessage.class);
        getEventBus().<ChatMessage>request("Message-Bus1", chatMessage, result -> { //Call on event-loop thread
            if (result.succeeded()) {
                logger.debug(Thread.currentThread() + "Post succeeded :: " + result.result().body());
                routingContext.response()
                        .putHeader("content-type", "application/json")
                        .setStatusCode(200)
                        .end("Accepted on Vertx");
            } else {
                logger.debug(Thread.currentThread() +" Message-Bus failed " + result.result().body());
                routingContext.response()
                        .setStatusCode(500)
                        .end("Vertx Service Unavailable");
            }
        });
    }

    public EventBus getEventBus() {
        return vertx.eventBus();
    }
}
