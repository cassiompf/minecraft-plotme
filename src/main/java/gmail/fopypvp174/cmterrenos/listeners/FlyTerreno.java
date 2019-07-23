package gmail.fopypvp174.cmterrenos.listeners;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.api.Utilidades;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class FlyTerreno implements Listener {
    private static Set<String> isFlying = new HashSet<>();
    private CmTerrenos plugin;

    public FlyTerreno(CmTerrenos plugin) {
        this.plugin = plugin;
    }

    public static Set<String> getIsFlying() {
        return isFlying;
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        if (!isFlying.contains(e.getPlayer().getName())) {
            return;
        }

        if (!e.getPlayer().isFlying()) {
            return;
        }

        HouseEntity house = Utilidades.getHomeLocation(e.getTo().toVector());

        if (house == null || !house.getDono().equals(e.getPlayer().getName()) ||
                e.getTo().toVector().isInAABB(house.getPositionMin(), house.getPositionMax())) {
            new BukkitRunnable() {
                boolean fora = true;
                int timer = 8;

                @Override
                public void run() {
                    Player player = Bukkit.getPlayer(e.getPlayer().getName());
                    if (player == null) {
                        cancel();
                    } else if (!player.isOnline()) {
                        cancel();
                    } else if (!fora) {
                        cancel();
                    } else if (timer == 0) {
                        player.setFlying(false);
                        player.setAllowFlight(false);
                        player.sendMessage(plugin.getFileConfig().getMessage("Fly_Desativado"));
                        isFlying.remove(player.getName());
                        cancel();
                    }
                    HouseEntity house = Utilidades.getHomeLocation(player.getLocation().toVector());
                    if (house != null) {
                        if (house.getDono().equals(player.getName())) {
                            fora = false;
                        }
                    }
                    player.sendMessage(plugin.getFileConfig().getMessage("Fly_Voltar_Terreno").replace("%s", String.valueOf(timer)));
                    timer--;
                }
            }.runTaskTimerAsynchronously(plugin, 0L, 20L);
        }
    }

    @EventHandler
    public void toggleFly(PlayerToggleFlightEvent e) {
        if (!isFlying.contains(e.getPlayer().getName())) {
            return;
        }
        HouseEntity house = Utilidades.getHomeLocation(e.getPlayer().getLocation().toVector());
        if (house == null || !e.getPlayer().getLocation()
                .toVector().isInAABB(house.getPositionMin(), house.getPositionMax())) {
            e.getPlayer().sendMessage(plugin.getFileConfig().getMessage("Fora_Terreno_Fly"));
            return;
        }

        e.getPlayer().sendMessage(plugin.getFileConfig().getMessage("Fora_Terreno_Fly"));
        e.getPlayer().setFlying(true);
        e.getPlayer().setAllowFlight(true);
    }
}
