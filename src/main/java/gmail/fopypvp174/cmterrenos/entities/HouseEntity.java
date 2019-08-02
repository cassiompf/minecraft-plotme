package gmail.fopypvp174.cmterrenos.entities;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HouseEntity {

    private String dono;
    private Integer home;
    private String world;
    private Integer nivelTerreno;
    private Vector positionMin;
    private Vector positionMax;
    private Vector upgradeMin;
    private Vector upgradeMax;
    private Set<String> friends;

    public HouseEntity() {
    }

    public void createPlayer(Player player, Vector[] upgrade, Vector[] position) {
        setDono(player.getName());
        setWorld(player.getWorld().getName());
        setFriends(new HashSet<>());
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

    public Set<String> getFriends() {
        return friends;
    }

    public void setFriends(Set<String> friends) {
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
        return dono.equals(that.dono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dono);
    }
}
