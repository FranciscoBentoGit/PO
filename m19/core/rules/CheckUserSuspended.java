package m19.core.rules;

import m19.core.User;
import m19.core.Work;
import m19.core.Date;
import m19.core.Request;

public class CheckUserSuspended extends Rules {

    public CheckUserSuspended(User user, Work work, int date, Request request) {
        super(user, work, date, request); 
    }

    @Override
    public int isValidRequest() {
        int previousRuleValidity = _checkRequest.isValidRequest();

        return previousRuleValidity == 0 ? (_user.getUserState().equals("SUSPENSO") ? 2 : 0) : previousRuleValidity;
    }
}