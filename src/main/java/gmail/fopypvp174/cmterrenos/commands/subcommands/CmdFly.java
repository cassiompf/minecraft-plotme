package gmail.fopypvp174.cmterrenos.commands.subcommands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.api.Utilidades;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import gmail.fopypvp174.cmterrenos.listeners.FlyTerreno;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdFly extends SubCommand {

    public CmdFly(CmTerrenos cmTerrenos) {
        super(cmTerrenos);

    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Somente_Jogador"));
            return;
        }

        if (args.length != 0) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Comando_Desconhecido"));
            return;
        }

        Player player = Bukkit.getPlayer(sender.getName());
        if (!player.hasPermission("terrenos.fly")
                && !player.hasPermission("terrenos.admin")) {
            player.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Sem_Permissao"));
            return;
        }

        HouseEntity house = Utilidades.getHomeLocation(player
                .getLocation().toVector());

        if (house == null ||
                !player.getLocation().toVector().isInAABB(house
                        .getPositionMin(), house.getPositionMax()) ||
                !house.getDono().equals(player.getName())) {
            player.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Fora_Terreno_Fly"));
            return;
        }

        if (FlyTerreno.getIsFlying().contains(player.getName())) {
            player.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Fly_Desativado"));
            FlyTerreno.getIsFlying().remove(player.getName());
            player.setAllowFlight(false);
        } else {
            player.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Fly_Ativado"));
            FlyTerreno.getIsFlying().add(player.getName());
            player.setAllowFlight(true);
        }
    }

    @Override
    public String name() {
        return "fly";
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[]{"voar"};
    }
}
