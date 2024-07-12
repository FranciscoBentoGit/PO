package m19.core.behaviour;

import java.io.Serializable;

import m19.core.User;

public abstract class Behaviour implements Serializable { 

	private static final long serialVersionUID = 201901101344L;

	protected int _userFailures;

	protected int _userSuccesses;
	
	public Behaviour(int userFailures, int userSuccesses) {
		_userFailures = userFailures;
		_userSuccesses = userSuccesses;
	}

	public abstract void userTimelyDelivery(User user);

	public abstract void userLateDelivery(User user);

	public abstract int getAllowedRequests();
}
