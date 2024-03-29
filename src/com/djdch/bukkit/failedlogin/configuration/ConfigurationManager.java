package com.djdch.bukkit.failedlogin.configuration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.djdch.bukkit.failedlogin.FailedLogin;

/**
 * Class who load and hold the configuration of this plugin.
 * 
 * @author DjDCH
 */
public class ConfigurationManager {
    /**
     * Contains the filename of the configuration file.
     */
    private static final String CONFIG_FILE = "failedlogin.properties";

    /**
     * Contains the max length for the message string.
     */
    private static final int MAX_MSG_LENGTH = 100;

    /**
     * Contains the array who hold the defaults settings of the plugin.
     */
    protected HashMap<String, Object> defaults = new HashMap<String, Object>();

    /**
     * Contains the array who hold the settings of the plugin.
     */
    protected HashMap<String, Object> values = new HashMap<String, Object>();

    /**
     * Contains the Logger instance.
     */
    protected Logger logger;

    /**
     * Contains the FailedLogin instance.
     */
    protected FailedLogin failedLogin;

    /**
     * Constructor for the initialization of the config manager.
     * 
     * @param failedLogin Contains the FailedLogin instance.
     */
    public ConfigurationManager(FailedLogin failedLogin) {
        this.failedLogin = failedLogin;
        this.logger = failedLogin.getPluginLogger();

        this.defaults.put("login_banned_msg", "You are banned from this server!");
        this.defaults.put("login_whitelist_msg", "You are not white-listed on this server!");
        this.defaults.put("login_full_msg", "The server is full!");
        this.defaults.put("login_other_msg", "You cannot login on this server!");
        this.defaults.put("kick_kicked_msg", "Kicked by admin.");
        this.defaults.put("kick_banned_msg", "Banned by admin.");
    }

    /**
     * Method who load the configuration from config file.
     * 
     * @throws ConfigurationException Throw a ConfigurationException if an error occur while loading the configuration.
     */
    public void loadConfig() throws ConfigurationException {
        if (!this.failedLogin.getDataFolder().exists()) {
            this.failedLogin.getDataFolder().mkdir();

            if (!this.failedLogin.getDataFolder().exists()) {
                this.logger.severe("Cannot creating configuration directory");
                throw new ConfigurationException();
            } else {
                this.logger.info("Configuration directory created");
            }
        }

        File properties = new File(failedLogin.getDataFolder().getAbsolutePath() + File.separator + CONFIG_FILE);

        if (!properties.exists()) {
            try {
                properties.createNewFile();

                FileWriter fw = new FileWriter(properties);

                // Default configuration file
                fw.write("# Message definitions\nlogin_banned_msg=You are banned from this server!\nlogin_whitelist_msg=You are not white-listed on this server!\nlogin_full_msg=The server is full!\nlogin_other_msg=You cannot login on this server!\nkick_kicked_msg=Kicked by admin.\nkick_banned_msg=Banned by admin.\n");
                fw.close();

            } catch (Exception e) {
                this.logger.severe("Cannot create configuration file");
                throw new ConfigurationException();
            }
            this.logger.info("Configuration file created");
        }

        try {
            Properties propConfig = new Properties();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(failedLogin.getDataFolder().getAbsolutePath() + File.separator + CONFIG_FILE));
            propConfig.load(stream);

            // Parse defaults properties
            for (Entry<String, Object> data : this.defaults.entrySet()) {
                String value = propConfig.getProperty(data.getKey());
                Object patate;

                if (value == null) {
                    this.logger.warning("Cannot get property " + data.getKey() + ", using default");
                    patate = data.getValue();
                } else {
                    if (value.length() > MAX_MSG_LENGTH) {
                        this.logger.warning("Message " + data.getKey() + " is too long, cutting the excess");
                        patate = value.substring(0, MAX_MSG_LENGTH);
                    } else {
                        patate = value;
                    }
                }

                this.values.put(data.getKey(), patate);
            }
        } catch (Exception e) {
            this.logger.severe("Cannot load configuration file");
            throw new ConfigurationException();
        }

        this.logger.info("Configuration file loaded");
    }

    /**
     * Accessor who return the asked configuration value associated to the given key.
     * 
     * @param key Contains the asked configuration key.
     * @return Contains the value associated to the given key.
     */
    public Object getValue(String key) {
        try {
            return this.values.get(key.toLowerCase());
        } catch (NullPointerException e) {
            this.logger.severe("Cannot find value for " + key);
            this.failedLogin.getServer().getPluginManager().disablePlugin(this.failedLogin);
        }

        return null;
    }
}
