package gmail.fopypvp174.cmterrenos.commands.subcommands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.api.Utilidades;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdTp extends SubCommand {
    public CmdTp(CmTerrenos cmTerrenos) {
        super(cmTerrenos);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Somente_Jogador"));
            return;
        }

        if (args.length > 1) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Comando_Desconhecido"));
            return;
        }

        if (!getCmTerrenos().getHouseCache().hasTerreno(sender.getName())) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Nao_Tem_Terreno"));
            return;
        }

        Player player = Bukkit.getPlayer(sender.getName());
        long quantidade = getCmTerrenos().getHouseCache().getAllHouses().stream()
                .filter(target -> target.getDono().equals(player.getName())).count();

        if (quantidade == 1) {
            Utilidades.teleportHouse(player, getCmTerrenos().getHouseCache().getHouseHome(player.getName(), 1));
            player.sendMessage(getCmTerrenos().getFileConfig().getMessage("Teleportado"));
            return;
        }

        if (args.length == 1 && quantidade > 1) {
            try {
                Integer home = Integer.parseInt(args[0]);
                HouseEntity house = getCmTerrenos().getHouseCache()
                        .getHouseHome(player.getName(), home);
                if (house == null) {
                    player.sendMessage(getCmTerrenos().getFileConfig()
                            .getMessage("Sem_Terreno_TP"));
                    return;
                }

                Utilidades.teleportHouse(player, house);
                player.sendMessage(getCmTerrenos().getFileConfig()
                        .getMessage("Teleportado"));
            } catch (NumberFormatException e) {
                player.sendMessage(getCmTerrenos().getFileConfig()
                        .getMessage("Somente_Numeros"));
            }
        }
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
