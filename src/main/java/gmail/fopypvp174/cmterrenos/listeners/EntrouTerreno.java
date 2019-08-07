package gmail.fopypvp174.cmterrenos.listeners;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.api.PacketBuilder;
import gmail.fopypvp174.cmterrenos.api.Utilidades;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class EntrouTerreno implements Listener {

    private CmTerrenos plugin;
    private Set<String> inHouse = new HashSet<>();

    public EntrouTerreno(CmTerrenos plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {

        if (!e.getTo().getWorld().getName().equalsIgnoreCase(plugin.getFileConfig().getWorldTerrain())) {
            inHouse.remove(e.getPlayer().getName());
            return;
        }

        if (e.getTo().getBlockZ() == e.getFrom().getBlockZ() &&
                e.getTo().getBlockX() == e.getFrom().getBlockX()) {
            return;
        }

        HouseEntity houseTo = Utilidades.getHomeLocation(e.getTo().toVector());

        if (houseTo != null && !houseTo.getDono().equalsIgnoreCase("spawn-protect")) {
            Vector toMax = new Vector(houseTo.getPositionMax().getBlockX() + 1,
                    houseTo.getPositionMax().getBlockY(), houseTo.getPositionMax().getBlockZ() + 1);

            if (e.getTo().toVector().isInAABB(houseTo.getPositionMin(), toMax)) {
                if (!inHouse.contains(e.getPlayer().getName())) {
                    if (houseTo.getDono().equals(e.getPlayer().getName())) {
                        PacketBuilder.sendActionBar(e.getPlayer(), "&eVocê entrou no seu terreno!");
                    } else {
                        PacketBuilder.sendActionBar(e.getPlayer(), "&eVocê entrou do terreno do jogador '&7" + houseTo.getDono() + "&e'.");
                    }
                    inHouse.add(e.getPlayer().getName());
                    return;
                }
            }
        }

        HouseEntity houseFrom = Utilidades.getHomeLocation(e.getFrom().toVector());

        if (houseFrom != null && !houseFrom.getDono().equalsIgnoreCase("spawn-protect")) {
            Vector fromMax = new Vector(houseFrom.getPositionMax().getBlockX() + 1,
                    houseFrom.getPositionMax().getBlockY(), houseFrom.getPositionMax().getBlockZ() + 1);

            if (!e.getTo().toVector().isInAABB(houseFrom.getPositionMin(), fromMax)) {
                if (inHouse.contains(e.getPlayer().getName())) {
                    if (houseFrom.getDono().equals(e.getPlayer().getName())) {
                        PacketBuilder.sendActionBar(e.getPlayer(), "&eVocê saiu do seu terreno!");
                    } else {
                        PacketBuilder.sendActionBar(e.getPlayer(), "&eVocê saiu do terreno do jogador '&7" + houseFrom.getDono() + "&e'.");
                    }
                    inHouse.remove(e.getPlayer().getName());
                }
            }
        }
    }
}