package io.stockgeeks.pure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolExecutor {
  private final KafkaProps kafkaProps;

  public ThreadPoolExecutor(KafkaProps kafkaProps) {
    this.kafkaProps = kafkaProps;
  }

  @Bean
  public ThreadPoolTaskExecutor clientTaskExecutor() {
    ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
    pool.setCorePoolSize(kafkaProps.getClient().getNumThreads());
    pool.setMaxPoolSize(kafkaProps.getClient().getNumThreads());
    pool.setQueueCapacity(100);
    pool.setWaitForTasksToCompleteOnShutdown(true);
    return pool;
  }

  @Bean
  public ThreadPoolTaskExecutor producerTaskExecutor() {
    ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
    pool.setCorePoolSize(kafkaProps.getProducer().getNumThreads());
    pool.setMaxPoolSize(kafkaProps.getProducer().getNumThreads());
    pool.setQueueCapacity(100);
    pool.setWaitForTasksToCompleteOnShutdown(true);
    return pool;
  }

}
