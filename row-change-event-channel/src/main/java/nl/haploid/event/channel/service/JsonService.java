package nl.haploid.event.channel.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsonService {

    private final ObjectMapper mapper = new ObjectMapper();

    public String toString(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }
}
