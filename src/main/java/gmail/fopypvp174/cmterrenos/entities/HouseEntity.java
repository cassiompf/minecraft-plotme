package gmail.fopypvp174.cmterrenos.entities;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Objects;

public class HouseEntity {

    private String dono;
    private Integer home;
    private String world;
    private Integer nivelTerreno;
    private Vector positionMin;
    private Vector positionMax;
    private Vector upgradeMin;
    private Vector upgradeMax;
    private List<String> friends;

    public HouseEntity() {
    }

    public void createSpawnProtect(Player player, Vector[] upgrade) {
        setDono("spawn-protect");
        setWorld(player.getWorld().getName());
        setFriends(null);
        setPositionMin(null);
        setPositionMax(null);
        setUpgradeMin(upgrade[0]);
        setUpgradeMax(upgrade[1]);
        setHome(1);
        setNivelTerreno(1);
    }

    public void createPlayer(Player player, Vector[] upgrade, Vector[] position) {
        setDono(player.getName());
        setWorld(player.getWorld().getName());
        setFriends(null);
        setPositionMin(position[0]);
        setPositionMax(position[1]);
        setUpgradeMin(upgrade[0]);
        setUpgradeMax(upgrade[1]);
        setHome(1);
        setNivelTerreno(1);
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public Vector getPositionMin() {
        return positionMin;
    }

    public void setPositionMin(Vector positionMin) {
        this.positionMin = positionMin;
    }

    public Vector getPositionMax() {
        return positionMax;
    }

    public void setPositionMax(Vector positionMax) {
        this.positionMax = positionMax;
    }

    public Vector getUpgradeMin() {
        return upgradeMin;
    }

    public void setUpgradeMin(Vector upgradeMin) {
        this.upgradeMin = upgradeMin;
    }

    public Vector getUpgradeMax() {
        return upgradeMax;
    }

    public void setUpgradeMax(Vector upgradeMax) {
        this.upgradeMax = upgradeMax;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public Integer getNivelTerreno() {
        return nivelTerreno;
    }

    public void setNivelTerreno(Integer nivelTerreno) {
        this.nivelTerreno = nivelTerreno;
    }

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseEntity that = (HouseEntity) o;
        return Objects.equals(dono, that.dono) &&
                Objects.equals(home, that.home);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dono, home);
    }
}
