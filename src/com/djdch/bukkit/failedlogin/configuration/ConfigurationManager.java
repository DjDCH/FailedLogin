package com.djdch.bukkit.failedlogin.configuration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map.Entry;

import com.djdch.bukkit.failedlogin.FailedLogin;
import com.djdch.bukkit.util.Logger;

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
    protected FailedLogin plugin;

    /**
     * Constructor for the initialization of the config manager.
     * 
     * @param plugin Contains the FailedLogin instance.
     */
    public ConfigurationManager(FailedLogin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();

        this.defaults.put("kick_banned_msg", "You are banned from this server!");
        this.defaults.put("kick_whitelist_msg", "You are not white-listed on this server!");
    }

    /**
     * Method who load the configuration from config file.
     * 
     * @throws ConfigException Throw a ConfigException if an error occur while loading the configuration.
     */
    public void loadConfig() throws ConfigurationException {
        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();

            if (!this.plugin.getDataFolder().exists()) {
                this.logger.severe("Cannot creating configuration directory.");
                throw new ConfigurationException();
            } else {
                this.logger.info("Configuration directory created.");
            }
        }

        File properties = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + CONFIG_FILE);

        if (!properties.exists()) {
            try {
                properties.createNewFile();

                FileWriter fw = new FileWriter(properties);

                // Default configuration file
                fw.write("# Message definitions\nkick_banned_msg=You are banned from this server!\nkick_whitelist_msg=You are not white-listed on this server!\n");
                fw.close();

            } catch (Exception e) {
                this.logger.severe("Cannot create configuration file.");
                throw new ConfigurationException();
            }
            this.logger.info("Configuration file created.");
        }

        try {
            Properties propConfig = new Properties();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(plugin.getDataFolder().getAbsolutePath() + File.separator + CONFIG_FILE));
            propConfig.load(stream);

            // Parse defaults properties
            for (Entry<String, Object> data : this.defaults.entrySet()) {
                String value = propConfig.getProperty(data.getKey());
                Object patate;

                if (value == null) {
                    this.logger.warning("Cannot get property " + data.getKey() + ", using default.");
                    patate = data.getValue();
                } else {
                    patate = value;
                }

                values.put(data.getKey(), patate);
            }
        } catch (Exception e) {
            this.logger.severe("Cannot load configuration file.");
            throw new ConfigurationException();
        }
        this.logger.info("Configuration file loaded.");
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
            this.logger.severe("Cannot find default value for " + key + ".");
            this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
        }

        return null;
    }
}
