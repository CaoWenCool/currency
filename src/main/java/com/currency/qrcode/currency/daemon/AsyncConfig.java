package com.currency.qrcode.currency.daemon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {
    @Value("${max.pool.size:20}")
    private int maxPoolSize;

    @Value("${core.pool.size:20}")
    private int corePoolSize;

    @Bean
    public AsyncTaskExecutor asyncTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(getMaxPoolSize());
        threadPoolTaskExecutor.setCorePoolSize(getCorePoolSize());
        threadPoolTaskExecutor.setThreadNamePrefix("async-thread-pool-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }
}
