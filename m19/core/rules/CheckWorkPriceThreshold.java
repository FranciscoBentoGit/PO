package m19.core.rules;

import m19.core.User;
import m19.core.Work;
import m19.core.Date;
import m19.core.Request;

public class CheckWorkPriceThreshold extends Rules {

    public CheckWorkPriceThreshold(User user, Work work, int date, Request request) {
        super(user, work, date, request); 
    }

    @Override
    public int isValidRequest() {
        int previousRuleValidity = _checkRequest.isValidRequest();
        if (_user.getUserBehaviour().toString().equals("CUMPRIDOR")) {
            return 0;
        }
        return previousRuleValidity == 0 ? (_work.getWorkPrice() > 25 ? 6 : 0) : previousRuleValidity;
    }
}