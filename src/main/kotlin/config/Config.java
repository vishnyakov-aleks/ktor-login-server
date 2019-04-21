package config;

/**
 * Created by aleksey on 29.01.17.
 */
public class Config {
    public static final String CONFIGURATION_FILE = "./config/Server.properties";


    public static boolean DEBUG;
    public static boolean DEV;
    public static String PGSQL_URL;
    public static String PGSQL_USER;
    public static String PGSQL_PASSWORD;
    public static String BOT_KEY;
    public static String CHAT_ID;
    public static String WEB_PORT;
    public static String DOMAIN;
    public static String SCHEME;

    public static void load() {
        final PropertiesParser serverSettings = new PropertiesParser(CONFIGURATION_FILE);

        DEBUG = serverSettings.getBoolean("DEBUG", true);
        DEV = serverSettings.getBoolean("DEV", true);
        PGSQL_URL = serverSettings.getString("PGSQL_URL", "jdbc:postgresql://localhost:5432/postgres?useSSL=false&useUnicode=true&serverTimezone=UTC");
        PGSQL_USER = serverSettings.getString("PGSQL_USER", "alekseyvishnyakov");
        PGSQL_PASSWORD = serverSettings.getString("PGSQL_PASSWORD", "");
        BOT_KEY = serverSettings.getString("BOT_KEY", "429096867:AAH6Q74yAdpLz5LI7S4Y2LcKWA3I3aOFUO4");
        CHAT_ID = serverSettings.getString("CHAT_ID", "-1001130637");
        WEB_PORT = serverSettings.getString("WEB_PORT", "4567");
        DOMAIN = serverSettings.getString("DOMAIN", "alot.pro");
        SCHEME = serverSettings.getString("SCHEME", "https");
    }
}
