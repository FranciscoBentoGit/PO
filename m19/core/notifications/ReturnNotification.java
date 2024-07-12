package m19.core.notifications;

import m19.core.Work;

public class ReturnNotification extends Notification {

    public ReturnNotification(Work work) {
        super(work);
    }

    public String getNotificationMessage() {
        return "ENTREGA: " + _work.getWorkDescription();
    }
}