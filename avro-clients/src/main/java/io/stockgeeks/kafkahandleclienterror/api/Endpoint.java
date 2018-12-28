package io.stockgeeks.kafkahandleclienterror.api;

import io.stockgeeks.kafkahandleclienterror.avro.SimpleMessage;
import io.stockgeeks.kafkahandleclienterror.repository.SimpleMessageProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
public class Endpoint {

  private final SimpleMessageProducer producer;

  public Endpoint(SimpleMessageProducer producer) {
    this.producer = producer;
  }

  @PostMapping("/publish")
  public ResponseEntity publishMessage(@RequestBody SimpleMessageRequest message) {
    log.info("Got message, will publish it with values, name: {}, throwError: {}", message.getTitle(), message.isThrowError());
    SimpleMessage simpleMessage = SimpleMessage.newBuilder().setTitle(message.getTitle()).setThrowError(message.isThrowError()).build();
    producer.publish(simpleMessage);
    return ResponseEntity.ok("success");
  }
}

@Data
class SimpleMessageRequest {
  private String title;
  private boolean throwError;
}