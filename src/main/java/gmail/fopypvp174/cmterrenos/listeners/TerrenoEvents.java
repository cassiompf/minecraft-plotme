package gmail.fopypvp174.cmterrenos.listeners;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.api.Utilidades;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TerrenoEvents implements Listener {

    private CmTerrenos plugin;

    public TerrenoEvents(CmTerrenos plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        String worldConfig = plugin.getFileConfig().getWorldTerrain();
        Player player = e.getPlayer();
        if (!player.getLocation().getWorld().getName().equalsIgnoreCase(worldConfig)) {
            return;
        }

        HouseEntity house = Utilidades.getHomeLocation(e.getBlockPlaced().getLocation().toVector());
        if (house == null) {
            return;
        }

        if (house.getFriends() != null) {
            if (house.getFriends().contains(player.getName())) {
                return;
            }
        }

        if (house.getDono().equalsIgnoreCase(player.getName())) {
            return;
        }

        if (player.hasPermission("terreno.placeblock")) {
            return;
        }

        if (e.getBlockPlaced().getLocation().toVector().isInAABB(house.getPositionMin(), house.getPositionMax())) {
            e.setCancelled(true);
            return;
        }

        player.sendMessage(plugin.getFileConfig().getMessage("Nao_Pode_Colocar").replace("%p", house.getDono()));

        e.setCancelled(true);
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        String worldConfig = plugin.getFileConfig().getWorldTerrain();
        Player player = e.getPlayer();
        if (!player.getLocation().getWorld().getName().equalsIgnoreCase(worldConfig)) {
            return;
        }

        HouseEntity house = Utilidades.getHomeLocation(e.getBlock().getLocation().toVector());
        if (house == null) {
            return;
        }

        if (house.getFriends() != null) {
            if (house.getFriends().contains(player.getName())) {
                return;
            }
        }

        if (house.getDono().equalsIgnoreCase(player.getName())) {
            return;
        }

        if (player.hasPermission("terreno.breakblock")) {
            return;
        }

        player.sendMessage(plugin.getFileConfig().getMessage("Nao_Pode_Quebrar").replace("%p", house.getDono()));

        e.setCancelled(true);
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent e) {
        String worldConfig = plugin.getFileConfig().getWorldTerrain();
        Player player = e.getPlayer();
        if (!player.getLocation().getWorld().getName().equalsIgnoreCase(worldConfig)) {
            return;
        }

        if (e.getClickedBlock() == null) {
            return;
        }
        Block block = e.getClickedBlock();
        if (block.getType() != Material.CHEST &&
                block.getType() != Material.TRAPPED_CHEST &&
                block.getType() != Material.PAINTING &&
                block.getType() != Material.ITEM_FRAME &&
                block.getType() != Material.FURNACE &&
                block.getType() != Material.DISPENSER &&
                block.getType() != Material.HOPPER &&
                block.getType() != Material.DROPPER &&
                block.getType() != Material.NOTE_BLOCK &&
                block.getType() != Material.ARMOR_STAND &&
                block.getType() != Material.BREWING_STAND &&
                block.getType() != Material.STANDING_BANNER) {
            return;
        }

        HouseEntity house = Utilidades.getHomeLocation(e.getClickedBlock().getLocation().toVector());

        if (house == null) {
            return;
        }

        if (house.getFriends().contains(player.getName())) {
            return;
        }

        if (house.getDono().equalsIgnoreCase(player.getName())) {
            return;
        }

        if (player.hasPermission("terreno.interact")) {
            return;
        }
        player.sendMessage(plugin.getFileConfig().getMessage("Nao_Pode_Interagir").replace("%p", house.getDono()));

        e.setCancelled(true);
    }
}
