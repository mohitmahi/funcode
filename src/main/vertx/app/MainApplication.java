package app;

import io.vertx.core.Vertx;
import model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import server.ChatServer;

import javax.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan("server")
public class MainApplication {

    @Autowired
    private ChatServer serverVerticle;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
        System.out.println("Started Chat Server");
    }

    @PostConstruct
    public void deployVerticle() {
        Vertx vertx = Vertx.vertx();
        vertx.eventBus().registerDefaultCodec(ChatMessage.class, new GenericCodec<>(ChatMessage.class));
        vertx.deployVerticle(serverVerticle, res -> { if(res.succeeded()) {
            System.out.println("Deployed serverVerticle" + res.result());
        } else {
            System.out.println("Failed serverVerticle" + res.result());
        }
        });
    }
}
