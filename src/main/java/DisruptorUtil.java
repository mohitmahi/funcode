import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.Data;

import java.util.concurrent.ThreadFactory;

/**
 * https://lmax-exchange.github.io/disruptor/developer-guide/index.html
 */
public class DisruptorUtil {

    public static void main(String[] args) {
        ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;

        WaitStrategy waitStrategy = new BusySpinWaitStrategy();
        Disruptor<ValueEvent> disruptor
                = new Disruptor<ValueEvent>(
                ValueEvent.EVENT_FACTORY,
                16,
                threadFactory,
                ProducerType.SINGLE,
                waitStrategy);


        disruptor.handleEventsWith(SingleEventPrintConsumer.getEventHandler());

        RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        for (int eventCount = 0; eventCount < 32; eventCount++) {
            long sequenceId = ringBuffer.next();
            ValueEvent valueEvent = ringBuffer.get(sequenceId);
            valueEvent.setValue(eventCount);
            ringBuffer.publish(sequenceId);
        }

    }

    @Data
    static class ValueEvent {
        private int value;
        public final static EventFactory<ValueEvent> EVENT_FACTORY = ValueEvent::new;
    }

    static class SingleEventPrintConsumer {
        public static EventHandler[] getEventHandler() {
            EventHandler<ValueEvent> eventHandler = (event, sequence, endOfBatch) -> print(event.getValue(), sequence);
            return new EventHandler[] { eventHandler };
        }

        private static void print(int id, long sequenceId) {
           System.out.println("Id is " + id + " sequence id that was used is " + sequenceId);
        }
    }
}
