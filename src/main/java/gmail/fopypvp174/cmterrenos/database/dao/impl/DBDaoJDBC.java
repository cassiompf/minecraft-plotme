package gmail.fopypvp174.cmterrenos.database.dao.impl;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.api.Utilidades;
import gmail.fopypvp174.cmterrenos.database.DBConnector;
import gmail.fopypvp174.cmterrenos.database.DatabaseType;
import gmail.fopypvp174.cmterrenos.database.dao.DatabaseDAO;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import gmail.fopypvp174.cmterrenos.entities.MysqlEntity;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DBDaoJDBC extends DBConnector implements DatabaseDAO {

    public DBDaoJDBC(DatabaseType databaseType, CmTerrenos plugin) {
        super(databaseType, plugin);
    }

    public DBDaoJDBC(DatabaseType databaseType, MysqlEntity mysqlEntity, CmTerrenos plugin) {
        super(databaseType, mysqlEntity, plugin);
    }

    @Override
    public void saveTerrains(Collection<HouseEntity> houses) {
        String update = "UPDATE terrenos_data SET nivel = '?', positionMin = '?', positionMax = '?', amigos = '?' WHERE player = '?' and home = '?';";
        try (Connection conn = getConnection()) {
            for (HouseEntity house : houses) {
                try (PreparedStatement stmt = conn.prepareStatement(update)) {
                    stmt.setInt(1, house.getNivelTerreno());
                    stmt.setString(2, Utilidades.serializeLocation(house.getPositionMin()));
                    stmt.setString(3, Utilidades.serializeLocation(house.getPositionMax()));
                    stmt.setString(4, Utilidades.serializeFriends(house.getFriends()));
                    stmt.setString(5, house.getDono());
                    stmt.setInt(6, house.getHome());
                    stmt.executeUpdate();
                }
            }
            getPlugin().getLogger().info("Dados salvos com sucesso!");
        } catch (SQLException e) {
            getPlugin().getLogger().info("Erro: " + e.getMessage() + " Método: saveTerrains()");
        }
    }

    @Override
    public void deleteTerrain(HouseEntity house) {
        String delete = "DELETE FROM terrenos_data WHERE player = '?' and home = '?';";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(delete)) {
            stmt.setString(1, house.getDono());
            stmt.setInt(2, house.getHome());
            stmt.executeUpdate();
            getPlugin().getLogger().info("Dados removidos com sucesso!");
        } catch (SQLException e) {
            getPlugin().getLogger().info("Erro: " + e.getMessage() + " Método: saveTerrains()");
        }
    }

    @Override
    public void setTerrain(HouseEntity house) {
        String insert = "INSERT INTO terrenos_data VALUES ('?', '?', '?', '?', '?', '?', '?', '?', '?');";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert)) {
            stmt.setString(1, house.getDono());
            stmt.setInt(2, house.getHome());
            stmt.setString(3, house.getWorld());
            stmt.setInt(4, house.getNivelTerreno());
            stmt.setString(5, Utilidades.serializeLocation(house.getPositionMin()));
            stmt.setString(6, Utilidades.serializeLocation(house.getPositionMax()));
            stmt.setString(7, Utilidades.serializeLocation(house.getUpgradeMin()));
            stmt.setString(8, Utilidades.serializeLocation(house.getUpgradeMax()));
            stmt.setString(9, Utilidades.serializeFriends(house.getFriends()));
            stmt.executeUpdate();
            getPlugin().getLogger().info("Terreno inserido na tabela com sucesso!");
        } catch (SQLException e) {
            getPlugin().getLogger().info("Erro: " + e.getMessage() + " Método: saveTerrains()");
        }
    }

    @Override
    public Set<HouseEntity> getTerrains() {
        Set<HouseEntity> houses = new HashSet<>();
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM terrenos_data;")) {
            HouseEntity house;
            while (rs.next()) {
                house = new HouseEntity();
                house.setDono(rs.getString("player"));
                house.setHome(rs.getInt("home"));
                house.setNivelTerreno(rs.getInt("nivel"));
                house.setWorld(rs.getString("world"));
                house.setFriends(Utilidades.deserializeFriends(rs.getString("amigos")));

                house.setPositionMin(Utilidades.deserializeLocation(rs.getString("positionMin")));
                house.setPositionMax(Utilidades.deserializeLocation(rs.getString("positionMax")));

                house.setUpgradeMin(Utilidades.deserializeLocation(rs.getString("upgradeMin")));
                house.setUpgradeMax(Utilidades.deserializeLocation(rs.getString("upgradeMax")));

                houses.add(house);
            }
        } catch (SQLException e) {
            getPlugin().getLogger().info("Erro: " + e.getMessage() + " Método: startTerrains()");
        }
        getPlugin().getLogger().info("Dados carregados com sucesso!");
        return houses;
    }
}
