package io.stockgeeks.pure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProps {

  private List<String> hosts;
  private KafkaProducer producer;
  private KafkaClient client;

  @Data
  public static class KafkaProducer {
    private int numThreads;
  }

  @Data
  public static class KafkaClient {
    private String groupId;
    private int numThreads;
  }

}
