package gmail.fopypvp174.cmterrenos.listeners;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.api.Utilidades;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
        if (!player.getLocation().getWorld().getName().equals(worldConfig)) {
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

        if (house.getDono().equals(player.getName())) {
            return;
        }

        if (player.hasPermission("terreno.placeblock")) {
            return;
        }

        if (!e.getBlockPlaced().getLocation().toVector().isInAABB(house.getPositionMin(), house.getPositionMax())) {
            return;
        }

        player.sendMessage(plugin.getFileConfig().getMessage("Nao_Pode_Colocar").replace("%p", house.getDono()));
        e.setCancelled(true);
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        String worldConfig = plugin.getFileConfig().getWorldTerrain();
        Player player = e.getPlayer();
        if (!player.getLocation().getWorld().getName().equals(worldConfig)) {
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

        if (house.getDono().equals(player.getName())) {
            return;
        }

        if (player.hasPermission("terreno.breakblock")) {
            return;
        }

        if (!e.getBlock().getLocation().toVector().isInAABB(house.getPositionMin(), house.getPositionMax())) {
            return;
        }

        player.sendMessage(plugin.getFileConfig().getMessage("Nao_Pode_Quebrar").replace("%p", house.getDono()));
        e.setCancelled(true);
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent e) {
        String worldConfig = plugin.getFileConfig().getWorldTerrain();
        Player player = e.getPlayer();

        if (!player.getLocation().getWorld().getName().equals(worldConfig)) {
            return;
        }

        if (e.getClickedBlock() == null) {
            return;
        }
        HouseEntity house = Utilidades.getHomeLocation(e.getClickedBlock().getLocation().toVector());

        if (house == null) {
            return;
        }

        if (house.getFriends() != null) {
            if (house.getFriends().contains(player.getName())) {
                return;
            }
        }

        if (house.getDono().equals(player.getName())) {
            return;
        }

        if (player.hasPermission("terreno.interact")) {
            return;
        }
        player.sendMessage(plugin.getFileConfig().getMessage("Nao_Pode_Interagir").replace("%p", house.getDono()));

        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void pvpTimeDayNight(EntityDamageByEntityEvent e) {

        if (!plugin.getFileConfig().isPvPActived()) {
            return;
        }
        String worldConfig = plugin.getFileConfig().getWorldTerrain();

        if (!e.getEntity().getLocation().getWorld().getName().equals(worldConfig) &&
                !e.getDamager().getLocation().getWorld().getName().equals(worldConfig)) {
            return;
        }

        if (!(e.getEntity() instanceof Player) && !(e.getEntity() instanceof Player)) {
            return;
        }

        if (plugin.isPvpEnable()) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }
}
