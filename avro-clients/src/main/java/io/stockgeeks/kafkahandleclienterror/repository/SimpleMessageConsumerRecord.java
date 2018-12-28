package io.stockgeeks.kafkahandleclienterror.repository;

import io.stockgeeks.kafkahandleclienterror.avro.SimpleMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class SimpleMessageConsumerRecord {

  @KafkaListener(
    id = "simple-message-consumer-record",
    autoStartup = "true",
    topics = "simple-message"
  )
  public void listen(ConsumerRecord<String, SimpleMessage> record) {
    log.info("Got message, offset: {}, key: {}", record.offset(), record.key());
    SimpleMessage simpleMessage = record.value();
    processMessage(simpleMessage);
  }

  void processMessage(SimpleMessage simpleMessage) {
    if(simpleMessage.getThrowError()) {
      throw new RuntimeException("this will throw an exception....");
    }
    log.info("No exceptions thrown...");
  }
}
