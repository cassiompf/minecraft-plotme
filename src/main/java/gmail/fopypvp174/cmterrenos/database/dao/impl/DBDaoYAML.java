package gmail.fopypvp174.cmterrenos.database.dao.impl;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.api.Utilidades;
import gmail.fopypvp174.cmterrenos.database.dao.DatabaseDAO;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import gmail.fopypvp174.cmterrenos.yaml.Config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DBDaoYAML extends Config implements DatabaseDAO {

    public DBDaoYAML(CmTerrenos plugin, String fileName) {
        super(plugin, fileName);
    }

    @Override
    public void setTerrain(HouseEntity house) {
        String terreno = "terrenos." + house.getDono() + "#" + house.getHome();
        try {
            getCustomConfig().set(terreno + ".world", house.getWorld());
            getCustomConfig().set(terreno + ".positionMin", Utilidades.serializeLocation(house.getPositionMin()));
            getCustomConfig().set(terreno + ".positionMax", Utilidades.serializeLocation(house.getPositionMax()));
            getCustomConfig().set(terreno + ".upgradeMin", Utilidades.serializeLocation(house.getUpgradeMin()));
            getCustomConfig().set(terreno + ".upgradeMax", Utilidades.serializeLocation(house.getUpgradeMax()));
            getCustomConfig().set(terreno + ".nivel", house.getNivelTerreno());
            getCustomConfig().set(terreno + ".amigos", Utilidades.serializeFriends(house.getFriends()));
            saveConfig();
            getPlugin().getLogger().info("Terreno inserido no arquivo com sucesso!");
        } catch (IOException e) {
            getPlugin().getLogger().info("Erro: " + e.getMessage() + " Método: setTerrain()");
        }
    }

    @Override
    public void saveTerrains(Collection<HouseEntity> houses) {
        try {
            for (HouseEntity data : houses) {
                String terreno = "terrenos." + data.getDono() + "#" + data.getHome();
                getCustomConfig().set(terreno + ".world", data.getWorld());
                getCustomConfig().set(terreno + ".positionMin", Utilidades.serializeLocation(data.getPositionMin()));
                getCustomConfig().set(terreno + ".positionMax", Utilidades.serializeLocation(data.getPositionMax()));
                getCustomConfig().set(terreno + ".upgradeMin", Utilidades.serializeLocation(data.getUpgradeMin()));
                getCustomConfig().set(terreno + ".upgradeMax", Utilidades.serializeLocation(data.getUpgradeMax()));
                getCustomConfig().set(terreno + ".nivel", data.getNivelTerreno());
                getCustomConfig().set(terreno + ".amigos", data.getFriends());
            }
            saveConfig();
            getPlugin().getLogger().info("Dados salvos com sucesso!");
        } catch (IOException e) {
            getPlugin().getLogger().info("Erro: " + e.getMessage() + " Método: setTerrain()");
        }
    }

    @Override
    public void deleteTerrain(HouseEntity house) {
        String terreno = "terrenos." + house.getDono() + "#" + house.getHome();
        try {
            getCustomConfig().set(terreno, null);
            saveConfig();
            getPlugin().getLogger().info("Terreno deletado do arquivo com sucesso!");
        } catch (IOException e) {
            getPlugin().getLogger().info("Erro: " + e.getMessage() + " Método: setTerrain()");
        }
    }

    @Override
    public void startTerrains() {
        Set<HouseEntity> houses = new HashSet<>();
        if (getCustomConfig().isConfigurationSection("terrenos")) {
            for (String name : getCustomConfig().getConfigurationSection("terrenos").getKeys(false)) {
                HouseEntity house = new HouseEntity();
                String[] casa = name.split("#");
                house.setDono(casa[0]);

                house.setHome(Integer.parseInt(casa[1]));

                String world = getCustomConfig().getString("terrenos." + name + ".world");
                house.setWorld(world);

                String positionMin = getCustomConfig().getString("terrenos." + name + ".positionMin");
                house.setPositionMin(Utilidades.deserializeLocation(positionMin));

                String positionMax = getCustomConfig().getString("terrenos." + name + ".positionMax");
                house.setPositionMax(Utilidades.deserializeLocation(positionMax));

                String upgradeMin = getCustomConfig().getString("terrenos." + name + ".upgradeMin");
                house.setUpgradeMin(Utilidades.deserializeLocation(upgradeMin));

                String upgradeMax = getCustomConfig().getString("terrenos." + name + ".upgradeMax");
                house.setUpgradeMax(Utilidades.deserializeLocation(upgradeMax));

                String friends = getCustomConfig().getString("terrenos." + name + ".amigos");
                house.setFriends(Utilidades.deserializeFriends(friends));

                Integer nivel = getCustomConfig().getInt("terrenos." + name + ".nivel");
                house.setNivelTerreno(nivel);

                houses.add(house);
            }
        }

        new Thread(() -> getPlugin().getHouseCache().init(houses)).start();

        getPlugin().getLogger().info("Dados carregados com sucesso!");
    }

}
