package m19.core.rules;

import m19.core.Request;
import m19.core.User;
import m19.core.Work;
import m19.core.Date;

public class CheckRequestTwice extends Rules {

    public CheckRequestTwice(User user, Work work, int date, Request request) {
        super(user, work, date, request); 
    }

    @Override
    public int isValidRequest() {
        int previousRuleValidity = _checkRequest.isValidRequest();

        return previousRuleValidity == 0 ? (_user.getRequestTwice(_work.getId()) ? 1 : 0) : previousRuleValidity;
    }
}