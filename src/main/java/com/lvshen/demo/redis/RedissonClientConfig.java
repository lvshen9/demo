package com.lvshen.demo.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-8-5 9:21
 * @since JDK 1.8
 */
@Configuration
public class RedissonClientConfig {
    @Value("${spring.redis.host}")
    private static String redisHost;
    @Value("${spring.redis.cluster.nodes}")
    private static String redisCluster;
    @Value("${spring.redis.password}")
    private static String redisPassword;
    @Value("${spring.redis.port}")
    private static String redisPort;

    @Autowired
    private ApplicationContext context;

    public String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }

    /**
     *
     *
     * @return
     */
    @Bean("redissonService")
    public RedissonClient getRedissonClient() {

        String activeProfile = getActiveProfile();
        Config config;
        if ("prod".equals(activeProfile)) {
            config = useClusterConfig();
        } else {
            config = useSingleConfig();
        }
        return Redisson.create(config);
    }

    private Config useSingleConfig() {
        Config config = new Config();
        StringBuilder sb = new StringBuilder("redis://");
        sb.append(redisHost).append(":").append(redisPort);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(sb.toString());
        singleServerConfig.setPassword(redisPassword);
        return config;
    }

    private Config useClusterConfig() {
        ResourceLoader loader = new DefaultResourceLoader();
        Config config = new Config();
        Resource resource = loader.getResource("redisson.yml");
        try {
            config = Config.fromYAML(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        clusterServersConfig.addNodeAddress(redisCluster);
        return config;
    }

    public void close(RedissonClient redissonClient) {
        redissonClient.shutdown();
    }
}
