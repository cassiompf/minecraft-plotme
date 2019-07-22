package gmail.fopypvp174.cmterrenos.commands.subcommands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.api.Utilidades;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class CmdCoord extends SubCommand {

    public CmdCoord(CmTerrenos cmTerrenos) {
        super(cmTerrenos);
    }

    @Override
    public void onCommand(CommandSender sender, ArrayList<String> args) {
        if (!(sender instanceof Player)) {
            return;
        }
        Player player = Bukkit.getPlayer(sender.getName());

        Vector[] value = Utilidades.calculeUpgrade(player.getLocation().toVector());
        player.sendMessage("min-X-Z: " + value[0].toString());
        player.sendMessage("max-X-Z: " + value[1].toString());
    }

    @Override
    public String name() {
        return "coord";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
