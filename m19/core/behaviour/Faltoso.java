package m19.core.behaviour;

import m19.core.User;

public class Faltoso extends Behaviour{

    public Faltoso(int userFailures, int userSuccesses) {
        super(userFailures, userSuccesses);
    }

    public void userTimelyDelivery(User user) {
        _userSuccesses++; 
        _userFailures = 0;
        if (_userSuccesses >= 3) {
            user.setUserBehaviour(new Regular(_userFailures, _userSuccesses));
        }
    }

    public void userLateDelivery(User user) {
        _userSuccesses = 0;
        _userFailures++;
    }

    public int getAllowedRequests() {
        return 1;
    }

    @Override
    public String toString() {
        return "FALTOSO";
    }
}