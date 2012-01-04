package com.djdch.bukkit.failedlogin.listeners;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.djdch.bukkit.failedlogin.FailedLogin;

/**
 * Class who listen for any player who tries to login.
 * 
 * @author DjDCH
 */
public class LoginListener extends PlayerListener {
	/**
	 * Contains the FailedLogin instance.
	 */
	protected FailedLogin failedLogin;
	
	/**
	 * Constructor for the initialization of the LoginListener class.
	 * 
	 * @param failedLogin Contains the FailedLogin instance.
	 */
	public LoginListener(FailedLogin failedLogin) {
		this.failedLogin = failedLogin;
	}
	
	/**
	 * Method who is called each time a player tries to login.
	 * 
	 * @param event Contains the PlayerLoginEvent instance.
	 */
	public void onPlayerLogin(PlayerLoginEvent event) {
		if (event.getResult() == Result.KICK_BANNED) {
			event.setKickMessage("You haven't paid for this month. Please proceed with the payment or contact the administrator.");
		} else if (event.getResult() == Result.KICK_WHITELIST) {
			event.setKickMessage("You are not white-listed on this server. Please contact the administrator.");
		}
	}
}
