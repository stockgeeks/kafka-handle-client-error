package io.stockgeeks.pure.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.stockgeeks.pure.config.KafkaProps;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.util.Properties;
import java.util.concurrent.Future;

@Slf4j
@Repository
public class StringJsonNodeClientProducer implements AutoCloseable {

  private final KafkaProps config;
  private Producer<String, JsonNode> kafkaProducer;

  public StringJsonNodeClientProducer(KafkaProps kafkaProps) {
    this.config = kafkaProps;
    kafkaProducer = new KafkaProducer<>(kafkaClientProperties());
  }

  public Future<RecordMetadata> send(String topic, String key, Object instance) {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.convertValue(instance, JsonNode.class);
    return kafkaProducer.send(new ProducerRecord<>(topic, key,
      jsonNode));
  }

  @PreDestroy
  public void close() {
    kafkaProducer.close();
  }

  private Properties kafkaClientProperties() {
    Properties properties = new Properties();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getHosts());
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return properties;
  }
}

