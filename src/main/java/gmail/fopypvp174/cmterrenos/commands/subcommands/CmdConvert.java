package gmail.fopypvp174.cmterrenos.commands.subcommands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.cache.dao.HouseDaoCache;
import gmail.fopypvp174.cmterrenos.database.DatabaseType;
import gmail.fopypvp174.cmterrenos.database.dao.DatabaseDAO;
import gmail.fopypvp174.cmterrenos.database.dao.impl.DBDaoJDBC;
import gmail.fopypvp174.cmterrenos.database.dao.impl.DBDaoYAML;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class CmdConvert extends SubCommand {
    public CmdConvert(CmTerrenos cmTerrenos) {
        super(cmTerrenos);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("terrenos.admin")) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Perm_Staff"));
            return;
        }

        if (args.length != 2) {
            sender.sendMessage(getCmTerrenos().getFileConfig().getMessage("Comando_Desconhecido"));
            return;
        }

        if (args[0].equalsIgnoreCase("yaml") &&
                args[1].equalsIgnoreCase("mysql")) {
            if (getCmTerrenos().getFileConfig().getDatabase().equalsIgnoreCase("MYSQL")) {
                new Thread(() -> {
                    DatabaseDAO yaml = new DBDaoYAML(getCmTerrenos(), "terrenos_data.yml");
                    DatabaseDAO jdbc = new DBDaoJDBC(DatabaseType.MYSQL, getCmTerrenos().getMysqlEntity(), getCmTerrenos());

                    Set<HouseEntity> yamlHouses = yaml.getTerrains();
                    Set<HouseEntity> jdbcHouses = jdbc.getTerrains();

                    for (HouseEntity yamlHouse : yamlHouses) {
                        if (!jdbcHouses.contains(yamlHouse)) {
                            jdbc.setTerrain(yamlHouse);
                        }
                    }
                    sender.sendMessage(getCmTerrenos().getFileConfig().getMessage("Convert_Yaml_to_Mysql"));
                }).start();
            }
        }

        if (args[0].equalsIgnoreCase("mysql") &&
                args[1].equalsIgnoreCase("yaml")) {
            if (getCmTerrenos().getFileConfig().getDatabase().equalsIgnoreCase("MYSQL")) {
                new Thread(() -> {
                    DatabaseDAO jdbc = new DBDaoJDBC(DatabaseType.MYSQL, getCmTerrenos().getMysqlEntity(), getCmTerrenos());
                    DatabaseDAO yaml = new DBDaoYAML(getCmTerrenos(), "terrenos_data.yml");

                    Set<HouseEntity> jdbcHouses = jdbc.getTerrains();
                    Set<HouseEntity> yamlHouses = yaml.getTerrains();

                    for (HouseEntity jdbcHouse : jdbcHouses) {
                        if (!yamlHouses.contains(jdbcHouse)) {
                            yaml.setTerrain(jdbcHouse);
                        }
                    }
                    sender.sendMessage(getCmTerrenos().getFileConfig().getMessage("Convert_Mysql_to_Yaml"));
                }).start();
            }
        }
    }

    @Override
    public String name() {
        return "converter";
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return null;
    }
}
