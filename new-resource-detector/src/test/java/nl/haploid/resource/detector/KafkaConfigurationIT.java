package nl.haploid.resource.detector;

import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KafkaConfigurationIT extends AbstractIT {

    @Autowired
    private ConsumerConnector kafkaConsumer;

    @Test
    public void testNothing() {

        // TODO: we should probably have a producer to really test this

        Assert.assertNotNull(kafkaConsumer);
        final Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put("test", new Integer(1));
        final Map<String, List<KafkaStream<byte[], byte[]>>> streams = kafkaConsumer.createMessageStreams(topicCountMap);
        Assert.assertNotNull(streams);
    }
}
