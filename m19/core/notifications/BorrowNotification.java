package m19.core.notifications;

import m19.core.Work;

public class BorrowNotification extends Notification {

    public BorrowNotification(Work work) {
        super(work);
    }

    public String getNotificationMessage() {
        return "REQUISIÇÃO: " + _work.getWorkDescription(); 
    }
}