package nl.haploid.octowight;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonMapper {

	private class JsonMapException extends RuntimeException {
		public JsonMapException(final String message, final Throwable cause) {
			super(message, cause);
		}
	}

	private final ObjectMapper mapper = new ObjectMapper();

	public <T> T parse(final String serialized, Class<T> targetClass) {
		try {
			return mapper.readValue(serialized, targetClass);
		} catch (IOException e) {
			throw new JsonMapException(String.format("Could not parse JSON: %s!", serialized), e);
		}
	}

	public String toString(final Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			throw new JsonMapException(String.format("Could not serialize object of type %s!", object.getClass().getCanonicalName()), e);
		}
	}
}
