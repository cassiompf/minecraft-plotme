package gmail.fopypvp174.cmterrenos.commands.subcommands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CmdFriend extends SubCommand {

    public CmdFriend(CmTerrenos cmTerrenos) {
        super(cmTerrenos);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Somente_Jogador"));
            return;
        }

        if (args.length != 2) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Comando_Desconhecido"));
            return;
        }

        Player player = Bukkit.getPlayer(sender.getName());

        if (!getCmTerrenos().getHouseCache().hasTerreno(player.getName())) {
            player.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Nao_Tem_Terreno"));
            return;
        }

        HouseEntity house = getCmTerrenos().getHouseCache()
                .getHouseLoc(player.getLocation().toVector());

        if (house == null) {
            player.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Precisa_Estar_Terreno"));
            return;
        }
        List<String> friends = house.getFriends();

        if (friends == null) {
            friends = new ArrayList<>();
        }

        if (args[0].equalsIgnoreCase("add") ||
                args[0].equalsIgnoreCase("adicionar")) {
            if (friends.add(args[1])) {
                player.sendMessage(getCmTerrenos().getFileConfig()
                        .getMessage("Amigo_Adicionado").replace("%p", args[1]));
            } else {
                player.sendMessage(getCmTerrenos().getFileConfig()
                        .getMessage("Amigo_Ja_Adicionado").replace("%p", args[1]));
            }
        } else if (args[0].equalsIgnoreCase("rem") ||
                args[0].equalsIgnoreCase("remover")) {
            if (friends.remove(args[1])) {
                player.sendMessage(getCmTerrenos().getFileConfig()
                        .getMessage("Amigo_Removido").replace("%p", args[1]));
            } else {
                player.sendMessage(getCmTerrenos().getFileConfig()
                        .getMessage("Amigo_Ja_Removido").replace("%p", args[1]));
            }
        } else {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Comando_Desconhecido"));
            return;
        }
        house.setFriends(friends);
    }

    @Override
    public String name() {
        return "friend";
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[]{"amigos", "amigo"};
    }
}