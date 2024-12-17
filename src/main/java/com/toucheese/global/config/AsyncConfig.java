package com.toucheese.global.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync // 비동기 처리를 활성화
public class AsyncConfig {

	@Bean(name = "customTaskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10); // 기본 스레드 개수
		executor.setMaxPoolSize(50); // 최대 스레드 개수
		executor.setQueueCapacity(100); // 작업 대기열 크기
		executor.setThreadNamePrefix("AsyncExecutor-"); // 스레드 이름 접두사
		executor.setWaitForTasksToCompleteOnShutdown(true); // 애플리케이션 종료 시 대기
		executor.setAwaitTerminationSeconds(30); // 종료 대기 시간 (초)
		executor.initialize(); // 초기화
		return executor;
	}
}