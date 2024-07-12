package m19.core.rules;

import m19.core.Request;
import m19.core.User;
import m19.core.Work;
import m19.core.Date;

public abstract class Rules extends Request {
    
    protected Request _checkRequest;

    public Rules(User user, Work work, int date, Request request) {
        super(user, work, date);
        _checkRequest = request;
    }

}