package nl.haploid.octowight.service;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

import java.util.Spliterator;
import java.util.function.Consumer;

class KafkaStreamSpliterator implements Spliterator<MessageAndMetadata<byte[], byte[]>> {

	private final ConsumerIterator<byte[], byte[]> iterator;

	public KafkaStreamSpliterator(final KafkaStream<byte[], byte[]> stream) {
		this.iterator = stream.iterator();
	}

	@Override
	public boolean tryAdvance(Consumer<? super MessageAndMetadata<byte[], byte[]>> action) {
		try {
			action.accept(iterator.next());
			return true;
		} catch (ConsumerTimeoutException e) {
			return false;
		}
	}

	@Override
	public Spliterator<MessageAndMetadata<byte[], byte[]>> trySplit() {
		return null;
	}

	@Override
	public long estimateSize() {
		return Long.MAX_VALUE;
	}

	@Override
	public int characteristics() {
		return 0;
	}
}

