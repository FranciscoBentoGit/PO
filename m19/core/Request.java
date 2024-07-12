package m19.core;

import java.io.Serializable;

import m19.core.User;
import m19.core.Work;

public abstract class Request implements Serializable {

    private static final long serialVersionUID = 201901101344L;

    protected int _deadline;
    protected User _user;
    protected Work _work;

    public Request(User user, Work work, int date) {
        _user = user;
        _work = work;
        _deadline = date + getAllowedDeadline();
    }

    public abstract int isValidRequest();

    public User getRequestUser() {
        return _user;
    }

    public Work getRequestWork() {
        return _work;
    }

    public int getRequestDeadline() {
        return _deadline;
    }

    public int getAllowedDeadline() {
        if (_user.getUserBehaviour().toString().equals("CUMPRIDOR")) {
            if (_work.getWorkNumbers() > 5) {
                return 30;
            } else {
                return _work.getWorkNumbers() == 1 ? 8 : 15;
            }
        } else if (_user.getUserBehaviour().toString().equals("NORMAL")) {
            if (_work.getWorkNumbers() > 5) {
                return 15;
            } else {
                return _work.getWorkNumbers() == 1 ? 3 : 8;
            }
        } else {
            return 2;
        }
    }

}