package com.djdch.bukkit.failedlogin;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.djdch.bukkit.failedlogin.listener.LoginListener;
import com.djdch.bukkit.util.Logger;

/**
 * Main class of the <b>FailedLogin</b> plugin for Bukkit.
 * <p>
 * Send custom kick message when the login process failed.
 * 
 * @author DjDCH
 */
public class FailedLogin extends JavaPlugin {
    /**
     * Contains the Logger instance.
     */
    protected final Logger logger = new Logger();

    /**
     * Contains the loginListener instance.
     */
    protected final LoginListener loginListener = new LoginListener(this);

    /**
     * Method execute when the plugin is enable.
     */
    public void onEnable() {
        this.logger.setName(getDescription().getName());

        // Register the plugin events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_LOGIN, loginListener, Event.Priority.Normal, this);

        this.logger.info("Version " + getDescription().getVersion() + " enable");
    }

    /**
     * Method execute when the plugin is disable.
     */
    public void onDisable() {
        this.logger.info("Version " + getDescription().getVersion() + " disable");
    }

    /**
     * Accessor who return the logger instance.
     * 
     * @return Logger instance.
     */
    public Logger getLogger() {
        return this.logger;
    }
}
