package gmail.fopypvp174.cmterrenos.yaml;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.util.List;

public class FileConfig extends Config {

    public FileConfig(CmTerrenos plugin, String fileName) {
        super(plugin, fileName);
    }

    public String getMessage(String args) {
        return ChatColor.translateAlternateColorCodes('&', getCustomConfig().getString("Mensagens." + args));
    }

    private String colorText(List<String> lines) {
        return colorText(String.join("\n", lines));
    }

    private String colorText(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public String getMessageList(String args) {
        return colorText(getCustomConfig().getStringList("Mensagens." + args));
    }

    public boolean isPvPActived() {
        return getCustomConfig().getBoolean("Configuração.pvpDayNight");
    }

    public String getWorldTerrain() {
        if (getCustomConfig().contains("Configuração.mundo_terrenos")) {
            return getCustomConfig().getString("Configuração.mundo_terrenos");
        }
        try {
            getCustomConfig().set("Configuração.mundo_terrenos", "world");
            saveConfig();
            return getCustomConfig().getString("Configuração.mundo_terrenos");
        } catch (IOException e) {
            getPlugin().getServer().getLogger().info("Não foi possível inserir uma informação no YAML! Método: getWorldTerrain()");
        }
        return null;
    }

    public String getDatabase() {
        if (getCustomConfig().contains("Configuração.database")) {
            return getCustomConfig().getString("Configuração.database");
        }
        try {
            getCustomConfig().set("Configuração.database", "YAML");
            saveConfig();
            return getCustomConfig().getString("Configuração.database");
        } catch (IOException e) {
            getPlugin().getServer().getLogger().info("Não foi possível inserir uma informação no YAML! Método: database()");
        }
        return null;
    }

    public String getMysqlUser() {
        if (getCustomConfig().contains("Configuração.mysql_user")) {
            return getCustomConfig().getString("Configuração.mysql_user");
        }
        try {
            getCustomConfig().set("Configuração.mysql_user", "root");
            saveConfig();
            return getCustomConfig().getString("Configuração.mysql_user");
        } catch (IOException e) {
            getPlugin().getServer().getLogger().info("Não foi possível inserir uma informação no YAML! Método: getMysqlUser()");
        }
        return null;
    }

    public String getMysqlHost() {
        if (getCustomConfig().contains("Configuração.mysql_host")) {
            return getCustomConfig().getString("Configuração.mysql_host");
        }
        try {
            getCustomConfig().set("Configuração.mysql_host", "localhost");
            saveConfig();
            return getCustomConfig().getString("Configuração.mysql_host");
        } catch (IOException e) {
            getPlugin().getServer().getLogger().info("Não foi possível inserir uma informação no YAML! Método: getMysqlHost()");
        }
        return null;
    }

    public String getMysqlDatabase() {
        if (getCustomConfig().contains("Configuração.mysql_database")) {
            return getCustomConfig().getString("Configuração.mysql_database");
        }
        try {
            getCustomConfig().set("Configuração.mysql_database", "servidor");
            saveConfig();
            return getCustomConfig().getString("Configuração.mysql_database");
        } catch (IOException e) {
            getPlugin().getServer().getLogger().info("Não foi possível inserir uma informação no YAML! Método: getMysqlDatabase()");
        }
        return null;
    }

    public String getMysqlPassword() {
        if (getCustomConfig().contains("Configuração.mysql_password")) {
            return getCustomConfig().getString("Configuração.mysql_password");
        }
        try {
            getCustomConfig().set("Configuração.mysql_password", "123");
            saveConfig();
            return getCustomConfig().getString("Configuração.mysql_password");
        } catch (IOException e) {
            getPlugin().getServer().getLogger().info("Não foi possível inserir uma informação no YAML! Método: getMysqlPassword()");
        }
        return null;
    }

    public Integer getMysqlPort() {
        if (getCustomConfig().contains("Configuração.mysql_port")) {
            return getCustomConfig().getInt("Configuração.mysql_port");
        }
        try {
            getCustomConfig().set("Configuração.mysql_port", "3306");
            saveConfig();
            return getCustomConfig().getInt("Configuração.mysql_port");
        } catch (IOException e) {
            getPlugin().getServer().getLogger().info("Não foi possível inserir uma informação no YAML! Método: getMysqlPort()");
        }
        return null;
    }

    /*
     * -1 quando não encontrar algum valor na config
     */
    public Integer getPrecoNivel(int nivelTerreno) {
        return getCustomConfig().getInt("Configuração.Valores.Nivel_" + nivelTerreno);
    }
}
