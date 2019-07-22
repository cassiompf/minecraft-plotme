package gmail.fopypvp174.cmterrenos.api;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;

public class Utilidades {

    public static HouseEntity getHomeLocation(Vector loc) {
        CmTerrenos cmTerrenos = CmTerrenos.getPlugin(CmTerrenos.class);
        Vector[] values = calculeUpgrade(loc);

        Collection<HouseEntity> houses = cmTerrenos.getHouseCache().getAllHouses();

        if (houses == null) {
            return null;
        }

        Iterator<HouseEntity> collection = houses.stream().filter(
                target -> target.getUpgradeMin().equals(values[0]) && target.getUpgradeMax().equals(values[1])
        ).iterator();

        return collection.hasNext() ? collection.next() : null;
    }

    public static void setBlockAroundHome(HouseEntity house, ItemStack itemStack) {
        Vector locMin = house.getPositionMin();
        Vector locMax = house.getPositionMax();
        World world = Bukkit.getWorld(house.getWorld());

        int y = 65;
        for (int j = locMin.getBlockX(); j <= locMax.getBlockX(); j++) {
            Location l1 = new Location(world, j,
                    y, locMin.getBlockZ());
            y = setBlock(l1, itemStack);
        }

        y = 65;
        for (int j = locMin.getBlockZ() + 1; j <= locMax.getBlockZ(); j++) {
            Location l1 = new Location(world, locMin.getBlockX(),
                    y, j);
            y = setBlock(l1, itemStack);
        }

        y = 65;
        for (int j = locMax.getBlockX(); j > locMin.getBlockX(); j--) {
            Location l1 = new Location(world, j,
                    y, locMax.getBlockZ());
            y = setBlock(l1, itemStack);
        }

        y = 65;
        for (int j = locMax.getBlockZ() - 1; j > locMin.getBlockZ(); j--) {
            Location l1 = new Location(world, locMax.getBlockX(),
                    y, j);
            y = setBlock(l1, itemStack);
        }
    }

    private static int setBlock(Location local, ItemStack itemStack) {
        setFloor(local);
        local.getBlock().setType(itemStack.getType());
        local.getBlock().setData(itemStack.getData().getData());
        return local.getBlockY();
    }

    private static void setFloor(Location local) {
        if (local.getBlock().getType().equals(Material.AIR)) {
            while (local.getBlock().getType().equals(Material.AIR) ||
                    local.getBlock().getType().equals(Material.DOUBLE_PLANT) ||
                    local.getBlock().getType().equals(Material.LONG_GRASS)) {
                local.setY(local.getBlockY() - 1);
            }
            local.setY(local.getBlockY() + 1);
        } else {
            while (!local.getBlock().getType().equals(Material.AIR) &&
                    !local.getBlock().getType().equals(Material.DOUBLE_PLANT) &&
                    !local.getBlock().getType().equals(Material.LONG_GRASS)) {
                local.setY(local.getBlockY() + 1);
            }
        }
    }

    public static void teleportHouse(Player player, HouseEntity house) {
        CmTerrenos cmTerrenos = CmTerrenos.getPlugin(CmTerrenos.class);
        if (house != null) {
            Location locMax = house.getUpgradeMax().toLocation(Bukkit.getWorld(cmTerrenos.getFileConfig().getWorldTerrain()));
            locMax.setY(60);

            locMax.setX(locMax.getBlockX() - 50);
            locMax.setZ(locMax.getBlockZ() - 50);

            setFloor(locMax);
            player.teleport(locMax);
        }
    }

    public static String serializeLocation(Vector l) {
        return l.getBlockX() + ","
                + l.getBlockY() + ","
                + l.getBlockZ();
    }

    public static Vector deserializeLocation(String s) {
        String[] location = s.split(",");
        return new Vector(
                Integer.parseInt(location[0]),
                Integer.parseInt(location[1]),
                Integer.parseInt(location[2]));
    }

    public static Set<String> deserializeFriends(String friends) {
        if (friends == null || friends.equals("")) {
            return null;
        }
        String[] location = friends.split(",");
        return new HashSet<>(Arrays.asList(location));
    }

    public static String serializeFriends(Set<String> friends) {
        return String.join(",", friends);
    }

    public static Vector[] calculeUpgrade(Vector vector) {

        if (vector.getBlockX() == 0 && vector.getBlockZ() == 0) {
            vector.setX(vector.getBlockX() + 1);
        }

        int minX = 0;
        int minZ = 0;
        int maxX = 0;
        int maxZ = 0;

        if (vector.getBlockX() > 0 && vector.getBlockZ() >= 0) {
            //Primeiro quadrante
            minX = formulaA(vector.getBlockX());
            maxX = formulaC(vector.getBlockX());
            minZ = formulaB(vector.getBlockZ());
            maxZ = formulaD(vector.getBlockZ());
        } else if (vector.getBlockX() <= 0 && vector.getBlockZ() > 0) {
            //Segundo quadrante
            minX = formulaD(vector.getBlockX()) * (-1);
            maxX = formulaB(vector.getBlockX()) * (-1);
            minZ = formulaA(vector.getBlockZ());
            maxZ = formulaC(vector.getBlockZ());
        } else if (vector.getBlockX() < 0 && vector.getBlockZ() <= 0) {
            //Terceiro quadrante
            minX = formulaC(vector.getBlockX()) * (-1);
            maxX = formulaA(vector.getBlockX()) * (-1);
            minZ = formulaD(vector.getBlockZ()) * (-1);
            maxZ = formulaB(vector.getBlockZ()) * (-1);
        } else if (vector.getBlockX() >= 0 && vector.getBlockZ() < 0) {
            //Quarto quadrante
            minX = formulaB(vector.getBlockX());
            maxX = formulaD(vector.getBlockX());
            minZ = formulaC(vector.getBlockZ()) * (-1);
            maxZ = formulaA(vector.getBlockZ()) * (-1);
        }

        Vector upgradeMin = new Vector(minX, 0, minZ);
        Vector upgradeMax = new Vector(maxX, 180, maxZ);

        return new Vector[]{upgradeMin, upgradeMax};
    }

    public static Vector[] calculePosition(Vector vector, int nivel) {
        int minX = positionMin(vector.getBlockX(), nivel);
        int minZ = positionMin(vector.getBlockZ(), nivel);
        int maxX = positionMax(vector.getBlockX(), nivel);
        int maxZ = positionMax(vector.getBlockZ(), nivel);

        return new Vector[]
                {new Vector(minX, 0, minZ),
                        new Vector(maxX, 180, maxZ)};
    }

    private static int positionMin(int num, int nivel) {
        return (num + 50) - (5 * nivel);
    }

    private static int positionMax(int num, int nivel) {
        return (num + 49) + (5 * nivel);
    }

    private static int formulaA(int value) {
        int num = Math.abs(value);

        if (num == 0) {
            num = 1;
        }
        return num - cut(num) + 1;
    }

    private static int formulaB(int value) {
        int num = Math.abs(value);

        if (num == 0) {
            num = 1;
        }
        return num - cut(num);
    }

    private static int formulaC(int value) {
        int num = Math.abs(value);

        if (num == 0) {
            num = 1;
        }
        return num - cut(num) + 100;
    }

    private static int formulaD(int value) {
        int num = Math.abs(value);

        if (num == 0) {
            num = 1;
        }
        return num - cut(num) + 99;
    }

    private static int cut(int value) {
        String stringNum = String.valueOf(value);
        int cutNum = 0;

        if (stringNum.length() > 2) {
            String aux = stringNum.substring(stringNum.length() - 2);
            if (!aux.equalsIgnoreCase("00")) {
                cutNum = Integer.parseInt(aux);
            }
        } else {
            cutNum = Integer.parseInt(stringNum);
        }
        return cutNum;
    }
}
