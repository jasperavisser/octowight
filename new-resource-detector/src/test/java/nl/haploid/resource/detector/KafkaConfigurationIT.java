package nl.haploid.resource.detector;

import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class KafkaConfigurationIT extends AbstractIT {

    @Autowired
    private ConsumerConnector kafkaConsumer;

    @Autowired
    private KafkaConfiguration kafkaConfiguration;

    @Value("${kafka.topic}")
    private String topic;

    @Test
    public void testCreateStream() {
        Assert.assertNotNull(kafkaConsumer);
        final KafkaStream<byte[], byte[]> stream = kafkaConfiguration.createStream(kafkaConsumer, topic);
        Assert.assertNotNull(stream);
    }
}
