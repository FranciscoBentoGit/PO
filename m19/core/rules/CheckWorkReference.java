package m19.core.rules;

import m19.core.User;
import m19.core.Work;
import m19.core.Date;
import m19.core.Request;
import m19.core.Category;

public class CheckWorkReference extends Rules {

    public CheckWorkReference(User user, Work work, int date, Request request) {
        super(user, work, date, request); 
    }

    @Override
    public int isValidRequest() {
        int previousRuleValidity = _checkRequest.isValidRequest();

        return previousRuleValidity == 0 ? (_work.getWorkCategory() == Category.REFERENCE ? 5 : 0) : previousRuleValidity;
    }
}