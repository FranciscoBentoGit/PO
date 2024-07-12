package m19.core;

import m19.core.User;
import m19.core.Work;
import m19.core.Date;

public class ConcreteRequest extends Request {
    
    public ConcreteRequest(User user, Work work, int date) {
        super(user, work, date);
    }

    public int isValidRequest() {
        return 0;
    }
}