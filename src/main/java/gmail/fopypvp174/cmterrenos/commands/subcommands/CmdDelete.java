package gmail.fopypvp174.cmterrenos.commands.subcommands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdDelete extends SubCommand {

    public CmdDelete(CmTerrenos cmTerrenos) {
        super(cmTerrenos);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Somente_Jogador"));
            return;
        }

        if (!sender.hasPermission("terreno.delete")) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Perm_Staff"));
            return;
        }


        if (args.length > 0) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Comando_Desconhecido"));
            return;
        }

        Player player = Bukkit.getPlayer(sender.getName());

        HouseEntity house = getCmTerrenos().getHouseCache()
                .getHouseLoc(player.getLocation().toVector());

        if (house == null) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Sem_Terreno_Deletar"));
            return;
        }

        getCmTerrenos().getHouseCache().removeTerrain(house);
        new Thread(() -> getCmTerrenos().getDatabaseDAO().deleteTerrain(house)).start();
    }

    @Override
    public String name() {
        return "deletar";
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[]{"delete", "del"};
    }
}
