package m19.core.behaviour;

import m19.core.User;
import m19.core.Work;

public class Cumpridor extends Behaviour {

    public Cumpridor(int userFailures, int userSuccesses) {
        super(userFailures, userSuccesses);
    }

    public void userTimelyDelivery(User user) {
        _userSuccesses++;
        _userFailures = 0;
    }

    public void userLateDelivery(User user) {
        _userFailures++;
        _userSuccesses = 0;
        if (_userFailures >= 1) {
            user.setUserBehaviour(new Regular(_userFailures, _userSuccesses));
        }
    }

    public int getAllowedRequests() {
        return 5;
    }

    @Override
    public String toString() {
        return "CUMPRIDOR";
    }
}