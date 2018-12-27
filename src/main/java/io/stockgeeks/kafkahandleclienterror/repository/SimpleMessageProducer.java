package io.stockgeeks.kafkahandleclienterror.repository;

import io.stockgeeks.kafkahandleclienterror.avro.SimpleMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SimpleMessageProducer {

  final static String TOPIC_NAME = "simple-message";
  private final KafkaTemplate<String, SimpleMessage> kafkaTemplate;

  public SimpleMessageProducer(KafkaTemplate kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void publish(SimpleMessage simpleMessage) {
    kafkaTemplate.send(TOPIC_NAME, simpleMessage.getTitle().toString(), simpleMessage);
  }
}
