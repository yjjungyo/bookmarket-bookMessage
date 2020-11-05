package bookmarket;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="BookAlarm_table")
public class BookAlarm {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long deliveryId;
    private String bookMessage;
    private String deliveryStatus;

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getBookMessage() {
        return bookMessage;
    }

    public void setBookMessage(String bookMessage) {
        this.bookMessage = bookMessage;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    @PrePersist
    public void onPrePersist(){
        MessageSended messageSended = new MessageSended();
        BeanUtils.copyProperties(this, messageSended);
        messageSended.publishAfterCommit();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
