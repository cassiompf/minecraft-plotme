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
    private Set<String> isOutTerrain = new HashSet<>();

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
                !e.getTo().toVector().isInAABB(house.getPositionMin(), house.getPositionMax())) {
            if (!isOutTerrain.contains(e.getPlayer().getName())) {
                isOutTerrain.add(e.getPlayer().getName());
                new BukkitRunnable() {
                    int timer = 8;

                    @Override
                    public void run() {
                        Player player = Bukkit.getPlayer(e.getPlayer().getName());
                        if (player == null) {
                            cancel();
                            return;
                        } else if (!player.isOnline()) {
                            cancel();
                            return;
                        } else if (timer == 0) {
                            player.sendMessage(plugin.getFileConfig().getMessage("Fly_Desativado"));
                            Bukkit.getScheduler().runTask(plugin, () -> {
                                player.setFlying(false);
                                player.setAllowFlight(false);
                            });
                            isFlying.remove(player.getName());
                            isOutTerrain.remove(player.getName());
                            cancel();
                            return;
                        }
                        HouseEntity house = Utilidades.getHomeLocation(player.getLocation().toVector());
                        if (house != null &&
                                player.getLocation().toVector().isInAABB(house.getPositionMin(), house.getPositionMax()) &&
                                house.getDono().equals(player.getName())) {
                            isOutTerrain.remove(player.getName());
                            cancel();
                            return;
                        }
                        player.sendMessage(plugin.getFileConfig().getMessage("Fly_Voltar_Terreno").replace("%s", String.valueOf(timer)));
                        timer--;
                    }
                }.runTaskTimerAsynchronously(plugin, 0L, 20L);
            }
        }
    }

    @EventHandler
    public void toggleFly(PlayerToggleFlightEvent e) {
        if (!isFlying.contains(e.getPlayer().getName())) {
            return;
        }
        HouseEntity house = Utilidades.getHomeLocation(e.getPlayer().getLocation().toVector());
        if (house == null ||
                !e.getPlayer().getLocation().toVector().isInAABB(house.getPositionMin(), house.getPositionMax())) {
            e.getPlayer().sendMessage(plugin.getFileConfig().getMessage("Fora_Terreno_Fly"));
            e.getPlayer().setFlying(false);
            e.setCancelled(true);
            return;
        }
        e.getPlayer().setFlying(true);
        e.getPlayer().setAllowFlight(true);
    }
}
