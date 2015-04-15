package nl.haploid.octowight.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisShardInfo;

@Configuration
public class RedisConfiguration {

	@Value("${octowight.redis.hostname:localhost}")
	private String hostname;

	@Value("${octowight.redis.port:6379}")
	private int port;

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		final JedisShardInfo shardInfo = new JedisShardInfo(hostname, port);
		return new JedisConnectionFactory(shardInfo);
	}

	// TODO: we don't seem to need this?
	// No qualifying bean of type [org.springframework.data.redis.core.RedisTemplate] is defined: expected single matching bean but found 2: redisTemplate,stringRedisTemplate
	@Bean
	public RedisTemplate<String, String> redisTemplate(final JedisConnectionFactory jedisConnectionFactory) {
		final RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		return template;
	}
}
