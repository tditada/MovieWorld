package ar.edu.itba.paw.g4.web.form;

import ar.edu.itba.paw.g4.model.user.User;

public class HiddenInterestingUserForm {
	private User interestingUser;
	private User interestedUser;
	
	
	public User getInterestingUser() {
		return interestingUser;
	}
	public void setInterestingUser(User interestingUser) {
		this.interestingUser = interestingUser;
	}
	public User getInterestedUser() {
		return interestedUser;
	}
	public void setInterestedUser(User interestedUser) {
		this.interestedUser = interestedUser;
	}
	
	
}
