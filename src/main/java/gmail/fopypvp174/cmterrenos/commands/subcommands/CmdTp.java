package gmail.fopypvp174.cmterrenos.commands.subcommands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.api.Utilidades;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdTp extends SubCommand {
    public CmdTp(CmTerrenos cmTerrenos) {
        super(cmTerrenos);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(getCmTerrenos().getFileConfig().getMessage("Comando_Desconhecido"));
            return;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Somente_Jogador"));
            return;
        }

        if (!getCmTerrenos().getHouseCache().hasTerreno(sender.getName())) {
            sender.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Nao_Tem_Terreno"));
            return;
        }

        Player player = Bukkit.getPlayer(sender.getName());

        Utilidades.teleportHouse(player, getCmTerrenos().getHouseCache().getHouse(player.getName()));
        player.sendMessage(getCmTerrenos().getFileConfig().getMessage("Teleportado"));
    }

    @Override
    public String name() {
        return "tp";
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[]{"teleport", "home", "teleportar"};
    }
}
