package kz.dev.triangle.core.config.kafka;

import kz.dev.triangle.core.config.kafka.config.BusConfiguration;
import kz.dev.triangle.core.config.kafka.stream.ITriangleEventStream;
import kz.dev.triangle.core.model.TriangleDTO;
import kz.dev.triangle.core.service.ITriangleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnBean(BusConfiguration.class)
@EnableBinding({ITriangleEventStream.class})
public class Receiver {

    private final ApplicationEventPublisher publisher;
    ITriangleService triangleService;

    public Receiver(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @StreamListener(target = ITriangleEventStream.INPUT, condition = "headers['type']=='triangleEvent'")
    public void handleTriangle(@Payload TriangleDTO triangle){
        log.info("Received Triangle event: {}, \nhandling... " + triangle);

        publisher.publishEvent(triangle);
    }

    @StreamListener(target = ITriangleEventStream.INPUT, condition = "headers['type']=='triangleEventErrorMessage'")
    public void handleError(@Payload String message){
        log.info("Received Triangle Error message: {}, \nhandling... " + message);
        publisher.publishEvent(message);
    }
}
