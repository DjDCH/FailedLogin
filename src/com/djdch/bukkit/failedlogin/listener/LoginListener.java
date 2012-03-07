package com.djdch.bukkit.failedlogin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.djdch.bukkit.failedlogin.FailedLogin;
import com.djdch.bukkit.failedlogin.configuration.ConfigurationManager;

/**
 * Class who listen for any player who tries to login.
 * 
 * @author DjDCH
 */
public class LoginListener implements Listener {
    /**
     * Contains the ConfigurationManager instance.
     */
    protected ConfigurationManager configurationManager;

    /**
     * Constructor for the initialization of the LoginListener class.
     * 
     * @param failedLogin Contains the FailedLogin instance.
     */
    public LoginListener(FailedLogin failedLogin) {
        this.configurationManager = failedLogin.getConfigurationManager();
    }

    /**
     * Method who is called each time a player tries to login.
     * 
     * @param event Contains the PlayerLoginEvent instance.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (event.getResult() == Result.KICK_BANNED) {
            event.setKickMessage((String) this.configurationManager.getValue("KICK_BANNED_MSG"));
        } else if (event.getResult() == Result.KICK_WHITELIST) {
            event.setKickMessage((String) this.configurationManager.getValue("KICK_WHITELIST_MSG"));
        }
    }
}
