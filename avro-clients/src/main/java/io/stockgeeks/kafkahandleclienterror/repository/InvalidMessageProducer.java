package io.stockgeeks.kafkahandleclienterror.repository;

import io.stockgeeks.kafkahandleclienterror.avro.InvalidMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InvalidMessageProducer {

  final static String TOPIC_NAME = "simple-message";
  private final KafkaTemplate<String, InvalidMessage> kafkaTemplate;

  public InvalidMessageProducer(KafkaTemplate kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void publish(InvalidMessage invalidMessage) {
    kafkaTemplate.send(TOPIC_NAME, invalidMessage.getCost().toString(), invalidMessage);
  }
}
