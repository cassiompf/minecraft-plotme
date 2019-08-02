package gmail.fopypvp174.cmterrenos.database;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.entities.MysqlEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    private Connection connection = null;
    private DatabaseType databaseType;
    private MysqlEntity mysqlEntity;
    private CmTerrenos plugin;

    public DBConnector(DatabaseType databaseType, CmTerrenos plugin) {
        this.databaseType = databaseType;
        this.plugin = plugin;
        createTable();
    }

    public DBConnector(DatabaseType databaseType, MysqlEntity mysqlEntity, CmTerrenos plugin) {
        this.databaseType = databaseType;
        this.mysqlEntity = mysqlEntity;
        this.plugin = plugin;
        createTable();
    }

    protected Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = "";
            if (databaseType.equals(DatabaseType.MYSQL)) {
                url = "jdbc:mysql://" + mysqlEntity.getHost() + ":" + mysqlEntity.getPort() + "/" + mysqlEntity.getDatabase() + "?useSSL=false";
            } else if (databaseType.equals(DatabaseType.SQLITE)) {
                url = "jdbc:sqlite:" + plugin.getDataFolder() + "/terrenos_data.db?useSSL=false";
            }
            try {
                if (databaseType.equals(DatabaseType.MYSQL)) {
                    connection = DriverManager.getConnection(url, mysqlEntity.getUser(), mysqlEntity.getPassword());
                } else if (databaseType.equals(DatabaseType.SQLITE)) {
                    connection = DriverManager.getConnection(url);
                }
            } catch (SQLException e) {
                getPlugin().getLogger().info("Erro: " + e.getMessage() + " Método: getConnection()");
            }
        }
        return connection;
    }

    private void createTable() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS terrenos_data (" +
                    "player VARCHAR(16) NOT NULL," +
                    "home INT(2) ZEROFILL NOT NULL," +
                    "world VARCHAR(16) NOT NULL," +
                    "nivel INT(1) NOT NULL," +
                    "positionMin TEXT NOT NULL," +
                    "positionMax TEXT NOT NULL," +
                    "upgradeMin TEXT NOT NULL," +
                    "upgradeMax TEXT NOT NULL," +
                    "amigos TEXT," +
                    "PRIMARY KEY (player, home)" +
                    ");";
            stmt.execute(sql);
        } catch (SQLException e) {
            getPlugin().getLogger().info("Erro: " + e.getMessage() + " Método: createTable()");
        }
    }

    public CmTerrenos getPlugin() {
        return plugin;
    }
}
