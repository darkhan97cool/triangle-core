package kz.dev.triangle.core.service;

import kz.dev.triangle.core.model.TriangleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaEventHandler {

    ITriangleService triangleService;

    @Autowired
    public KafkaEventHandler(ITriangleService triangleService) {
        this.triangleService = triangleService;
    }

    @EventListener
    public void handleTriangleEvent(TriangleDTO triangleDTO) {

        triangleService.saveTriangle(triangleDTO);

    }

    @EventListener
    public void handleErrorMessageEvent(String msg) {

        log.info("Received message: {}",msg);

    }


}
