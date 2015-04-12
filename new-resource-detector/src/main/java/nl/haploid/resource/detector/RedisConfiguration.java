package nl.haploid.resource.detector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisShardInfo;

@Configuration
public class RedisConfiguration {

    @Value("${redis.hostname:localhost}")
    private String hostname;

    @Value("${redis.port:6379}")
    private int port;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        final JedisShardInfo shardInfo = new JedisShardInfo(hostname, port);
        return new JedisConnectionFactory(shardInfo);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
