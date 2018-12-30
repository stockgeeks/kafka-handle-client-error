package io.stockgeeks.pure.repository;

import com.fasterxml.jackson.databind.JsonNode;
import io.stockgeeks.pure.config.KafkaProps;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Scope("prototype")
@Slf4j
public class StringJsonNodeClientConsumer {

    private KafkaProps config;

    public StringJsonNodeClientConsumer(KafkaProps config) {
        this.config = config;
    }

    @Async("clientTaskExecutor")
    public void startConsumer(String groupId, Collection<String> topicNames) {
        log.info("StringJsonNodeClientConsumer.startConsumer");
        final KafkaConsumer<String, JsonNode> kafkaConsumer = new KafkaConsumer<>(loadConsumerConfigProperties(groupId));
        kafkaConsumer.subscribe(topicNames);
        long counter = 0L;
        // we will start pooling for entries
        while (true) {
            ConsumerRecords<String, JsonNode> records = kafkaConsumer.poll(300);
            for (ConsumerRecord<String, JsonNode> record : records) {
                if (counter % 1000 == 0) {
                    log.info("Record: {}", record.toString());
                }
                counter++;
            }
        }
    }

    private Map<String, Object> loadConsumerConfigProperties(String groupId) {
        Map<String, Object> consumerConfigProperties = new HashMap<>();
        consumerConfigProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getHosts());
        consumerConfigProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        consumerConfigProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfigProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        consumerConfigProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return consumerConfigProperties;
    }
}
