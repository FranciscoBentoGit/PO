package m19.core.notifications;

import java.io.Serializable;

import m19.core.Work;

public abstract class Notification implements Serializable {

    private static final long serialVersionUID = 201901101344L;

    protected Work _work;

    public Notification(Work work) {
        _work = work;
    }

    public abstract String getNotificationMessage();
}