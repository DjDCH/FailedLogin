package com.djdch.bukkit.failedlogin;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.djdch.bukkit.failedlogin.configuration.ConfigurationException;
import com.djdch.bukkit.failedlogin.configuration.ConfigurationManager;
import com.djdch.bukkit.failedlogin.listener.LoginListener;

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
    protected Logger logger;

    /**
     * Contains the ConfigurationManager instance.
     */
    protected ConfigurationManager configurationManager;

    /**
     * Contains the loginListener instance.
     */
    protected LoginListener loginListener;

    /**
     * Method execute when the plugin is enable.
     */
    public void onEnable() {
        this.logger = this.getLogger();
        this.configurationManager = new ConfigurationManager(this);
        this.loginListener = new LoginListener(this);

        // Load the configuration
        try {
            this.configurationManager.loadConfig();
        } catch (ConfigurationException e) {
            setEnabled(false);
            return;
        }

        this.getServer().getPluginManager().registerEvents(this.loginListener, this);
    }

    /**
     * Method execute when the plugin is disable.
     */
    public void onDisable() {
    }

    /**
     * Accessor who return the plugin logger instance.
     * 
     * @return Logger instance.
     */
    public Logger getPluginLogger() {
        return this.logger;
    }

    /**
     * Accessor who return the configuration manager instance.
     * 
     * @return ConfigurationManager instance.
     */
    public ConfigurationManager getConfigurationManager() {
        return this.configurationManager;
    }
}
