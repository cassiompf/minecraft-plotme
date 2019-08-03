package gmail.fopypvp174.cmterrenos;

import gmail.fopypvp174.cmterrenos.cache.dao.HouseDaoCache;
import gmail.fopypvp174.cmterrenos.commands.CommandManager;
import gmail.fopypvp174.cmterrenos.database.DatabaseType;
import gmail.fopypvp174.cmterrenos.database.dao.DatabaseDAO;
import gmail.fopypvp174.cmterrenos.database.dao.impl.DBDaoJDBC;
import gmail.fopypvp174.cmterrenos.database.dao.impl.DBDaoYAML;
import gmail.fopypvp174.cmterrenos.entities.MysqlEntity;
import gmail.fopypvp174.cmterrenos.listeners.EntrouTerreno;
import gmail.fopypvp174.cmterrenos.listeners.FlyTerreno;
import gmail.fopypvp174.cmterrenos.listeners.TerrenoEvents;
import gmail.fopypvp174.cmterrenos.yaml.FileConfig;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class CmTerrenos extends JavaPlugin {

    private FileConfig fileConfig;
    private Economy econ;
    private DatabaseDAO databaseDAO;
    private HouseDaoCache houseCache;
    boolean pvpEnable = false;

    @Override
    public void onEnable() {
        if (setupVault() == false) {
            Bukkit.getLogger().info(String.format("[%s] Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }
        fileConfig = new FileConfig(this, "configurar.yml");
        if (fileConfig.getDatabase().equalsIgnoreCase("YAML")) {
            databaseDAO = new DBDaoYAML(this, "terrenos_data.yml");
        } else if (fileConfig.getDatabase().equalsIgnoreCase("SQLITE")) {
            databaseDAO = new DBDaoJDBC(DatabaseType.SQLITE, this);
        } else if (fileConfig.getDatabase().equalsIgnoreCase("MYSQL")) {
            MysqlEntity mysqlEntity = new MysqlEntity();
            mysqlEntity.setDatabase(fileConfig.getMysqlDatabase());
            mysqlEntity.setHost(fileConfig.getMysqlHost());
            mysqlEntity.setPort(fileConfig.getMysqlPort());
            mysqlEntity.setUser(fileConfig.getMysqlUser());
            mysqlEntity.setPassword(fileConfig.getMysqlPassword());
            databaseDAO = new DBDaoJDBC(DatabaseType.MYSQL, mysqlEntity, this);
        }
        houseCache = new HouseDaoCache();
        new Thread(() -> databaseDAO.startTerrains()).start();

        new CommandManager(this).setup();
        getServer().getPluginManager().registerEvents(new TerrenoEvents(this),
                this);
        getServer().getPluginManager().registerEvents(new FlyTerreno(this), this);
        getServer().getPluginManager().registerEvents(new EntrouTerreno(this), this);

        if (fileConfig.isPvPActived()) {
            startTimer();
        }
    }

    @Override
    public void onDisable() {
        new Thread(() -> databaseDAO.saveTerrains(getHouseCache().getAllHouses())).start();
    }

    private void startTimer() {
        World world = getServer().getWorld(fileConfig.getWorldTerrain());
        world.setTime(0L);

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            if (!pvpEnable && world.getTime() >= 13000) {
                pvpEnable = true;
                Bukkit.broadcastMessage(fileConfig.getMessageList("PvP_Ativado"));

            } else if (pvpEnable && world.getTime() < 13000) {
                Bukkit.broadcastMessage(fileConfig.getMessageList("PvP_Desativado"));

                pvpEnable = false;
            }
        }, 0L, 20 * 5);
    }

    private boolean setupVault() {
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            econ = rsp.getProvider();
            return econ != null;
        }
        return false;
    }

    public Economy getEcon() {
        return econ;
    }

    public FileConfig getFileConfig() {
        return fileConfig;
    }

    public HouseDaoCache getHouseCache() {
        return houseCache;
    }

    public DatabaseDAO getDatabaseDAO() {
        return databaseDAO;
    }

    public boolean isPvpEnable() {
        return pvpEnable;
    }
}
