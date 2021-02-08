package kz.dev.triangle.core.config.kafka.stream;

import kz.dev.triangle.core.config.kafka.config.BusConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface ITriangleEventStream {

    String INPUT = "triangleEvent";

    @Input(INPUT)
    @ConditionalOnBean(BusConfiguration.class)
    MessageChannel activeChangeProcess();
}
