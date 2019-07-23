package gmail.fopypvp174.cmterrenos.cache.dao;

import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Set;

public interface HouseDAO {

    void setTerrain(HouseEntity houseEntity);

    HouseEntity getHouse(String player);

    Collection<HouseEntity> getAllHouses();

    boolean hasTerreno(String owner);

    String getWorldHouse(String playerName);

    Integer getNivelTerreno(String playerName);

    Set<String> getAllFriends(String owner);

    boolean removeFriend(String owner, String friend);

    boolean addFriend(String owner, String friend);

    void setPositionMax(String playerHouse, Vector location);

    void setPositionMin(String playerHouse, Vector location);

    Vector getPositionMax(String playerHouse);

    Vector getPositionMin(String playerHouse);

    void setUpgradeMax(String playerHouse, Vector location);

    void setUpgradeMin(String playerHouse, Vector location);

    Vector getUpgradeMax(String playerHouse);

    Vector getUpgradeMin(String playerHouse);

    HouseEntity removeTerrain(String playerHouse);

}
