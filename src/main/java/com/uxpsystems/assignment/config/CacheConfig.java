package com.uxpsystems.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.MaxSizeConfig.MaxSizePolicy;

/**
 * 
 * This class has cache configuration to be used
 * 
 * @author Umesh.Chavan
 *
 */
@Configuration
public class CacheConfig {

	@Bean
	public Config configure() {
		return new Config().setInstanceName("hazelcastInstance")
				.addMapConfig(new MapConfig().setName("UserCache")
						.setMaxSizeConfig(new MaxSizeConfig(200, MaxSizePolicy.FREE_HEAP_SIZE))
						.setEvictionPolicy(EvictionPolicy.LRU).setTimeToLiveSeconds(2000));
	}

}
