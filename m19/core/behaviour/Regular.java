package m19.core.behaviour;

import m19.core.User;

public class Regular extends Behaviour {

    public Regular(int userFailures, int userSuccesses) {
        super(userFailures, userSuccesses);
    }

    public void userTimelyDelivery(User user) {
        _userSuccesses++; 
        _userFailures = 0;
        if (_userSuccesses >= 5) {
            user.setUserBehaviour(new Cumpridor(_userFailures, _userSuccesses));
        }
    }

    public void userLateDelivery(User user) {
        _userFailures++;
        _userSuccesses = 0;
        if (_userFailures >= 3) {
            user.setUserBehaviour(new Faltoso(_userFailures, _userSuccesses));
        }
    }

    public int getAllowedRequests() {
        return 3;
    }

    @Override
    public String toString() {
        return "NORMAL";
    }
}