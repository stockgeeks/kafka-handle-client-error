package io.stockgeeks.kafkahandleclienterror.repository;

import io.stockgeeks.kafkahandleclienterror.avro.SimpleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class SimpleMessageConsumer {

  @KafkaListener(
    id = "simple-message-consumer",
    autoStartup = "true",
    topics = "simple-message"
  )
  public void listen(SimpleMessage simpleMessage) {
    log.info("Got message, title: {}, throwError: {}", simpleMessage.getTitle(), simpleMessage.getThrowError());
    processMessage(simpleMessage);
  }

  private void processMessage(SimpleMessage simpleMessage) {
    if(simpleMessage.getThrowError()) {
      throw new RuntimeException("this will throw an exception....");
    }
    log.info("yey.... no exceptions thrown...");
  }
}
