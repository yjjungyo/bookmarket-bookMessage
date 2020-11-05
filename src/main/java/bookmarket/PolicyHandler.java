package bookmarket;

import bookmarket.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired
    BookAlarmRepository bookAlarmRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }



    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryStatusChanged_SendBookMessage(@Payload Shipped shipped){

        if(shipped.isMe()){
            System.out.println("##### listener SendBookMessage : " + shipped.toJson());

            BookAlarm bookAlarm = new BookAlarm();
            bookAlarm.setDeliveryId(shipped.getId());
            bookAlarm.setDeliveryStatus(shipped.getStatus());
            bookAlarm.setBookMessage("Book Delivery Status Changed : " + shipped.getStatus());

            bookAlarmRepository.save(bookAlarm);
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryStatusChanged_SendBookMessage(@Payload DeliveryCanceled deliveryCanceled){

        if(deliveryCanceled.isMe()){
            System.out.println("##### listener SendBookMessage : " + deliveryCanceled.toJson());

            BookAlarm bookAlarm = new BookAlarm();
            bookAlarm.setDeliveryId(deliveryCanceled.getId());
            bookAlarm.setDeliveryStatus(deliveryCanceled.getStatus());
            bookAlarm.setBookMessage("Book Delivery Status Changed : " + deliveryCanceled.getStatus());

            bookAlarmRepository.save(bookAlarm);
        }
    }

}
