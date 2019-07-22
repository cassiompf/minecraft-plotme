package gmail.fopypvp174.cmterrenos.cache.dao;

import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import org.bukkit.util.Vector;

import java.util.*;

public class HouseDaoCache implements HouseDAO {

    private Map<String, HouseEntity> houses;

    public HouseDaoCache() {
        this.houses = new HashMap<>();
    }

    @Override
    public void setTerrain(HouseEntity houseEntity) {
        houses.put(houseEntity.getDono(), houseEntity);
    }

    public void init(Set<HouseEntity> houses) {
        houses.forEach(target -> this.houses.put(target.getDono(), target));
    }

    @Override
    public HouseEntity getHouse(String player) {
        if (hasTerreno(player)) {
            return houses.get(player);
        }
        return null;
    }

    @Override
    public Collection<HouseEntity> getAllHouses() {
        return houses.values();
    }

    @Override
    public boolean hasTerreno(String owner) {
        return houses.containsKey(owner);
    }

    @Override
    public String getWorldHouse(String playerName) {
        if (hasTerreno(playerName)) {
            return houses.get(playerName).getWorld();
        }
        return null;
    }

    @Override
    public Integer getNivelTerreno(String playerName) {
        if (hasTerreno(playerName)) {
            return houses.get(playerName).getNivelTerreno();
        }
        return null;
    }

    @Override
    public Set<String> getAllFriends(String owner) {
        if (hasTerreno(owner)) {
            return houses.get(owner).getFriends();
        }
        return null;
    }

    @Override
    public boolean removeFriend(String owner, String friend) {
        Collection<String> friends = getAllFriends(owner);

        if (friends != null) {
            return friends.remove(friend);
        }
        return false;
    }

    @Override
    public boolean addFriend(String owner, String friend) {
        Set<String> friends = getAllFriends(owner);

        if (friends == null) {
            friends = new HashSet<>(Arrays.asList(
                    friend
            ));
            houses.get(owner).setFriends(friends);
            return true;
        }
        return friends.add(friend);
    }

    @Override
    public void setPositionMax(String playerHouse, Vector location) {
        houses.get(playerHouse).setPositionMax(location);
    }

    @Override
    public void setPositionMin(String playerHouse, Vector location) {
        houses.get(playerHouse).setPositionMin(location);
    }

    @Override
    public Vector getPositionMax(String playerHouse) {
        return houses.get(playerHouse).getPositionMax();
    }

    @Override
    public Vector getPositionMin(String playerHouse) {
        return houses.get(playerHouse).getPositionMin();
    }

    @Override
    public void setUpgradeMax(String playerHouse, Vector location) {
        houses.get(playerHouse).setUpgradeMax(location);

    }

    @Override
    public void setUpgradeMin(String playerHouse, Vector location) {
        houses.get(playerHouse).setUpgradeMin(location);
    }

    @Override
    public Vector getUpgradeMax(String playerHouse) {
        return houses.get(playerHouse).getUpgradeMax();
    }

    @Override
    public Vector getUpgradeMin(String playerHouse) {
        return houses.get(playerHouse).getUpgradeMin();
    }
}
