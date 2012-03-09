package com.djdch.bukkit.failedlogin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
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
     * Default Minecraft kicking reason.
     */
    protected static final String KICKING_REASON = "Kicked by admin";

    /**
     * Default Minecraft banning reason.
     */
    protected static final String BANNING_REASON = "Banned by admin.";

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
            event.setKickMessage((String) this.configurationManager.getValue("LOGIN_BANNED_MSG"));
        } else if (event.getResult() == Result.KICK_WHITELIST) {
            event.setKickMessage((String) this.configurationManager.getValue("LOGIN_WHITELIST_MSG"));
        } else if (event.getResult() == Result.KICK_FULL) {
            event.setKickMessage((String) this.configurationManager.getValue("LOGIN_FULL_MSG"));
        } else if (event.getResult() == Result.KICK_OTHER) {
            event.setKickMessage((String) this.configurationManager.getValue("LOGIN_OTHER_MSG"));
        }
    }

    /**
     * Method who is called each time a player is kicked.
     * 
     * @param event Contains the PlayerKickEvent instance.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerKick(PlayerKickEvent event) {
        if (event.getReason().equals(KICKING_REASON)) {
            event.setReason((String) this.configurationManager.getValue("KICK_KICKED_MSG"));
        } else if (event.getReason().equals(BANNING_REASON)) {
            event.setReason((String) this.configurationManager.getValue("KICK_BANNED_MSG"));
        }
    }
}
