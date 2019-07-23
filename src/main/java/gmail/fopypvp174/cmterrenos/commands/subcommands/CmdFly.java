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
        if (args.length != 0) {
            sender.sendMessage(getCmTerrenos().getFileConfig().getMessage("Comando_Desconhecido"));
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Somente_Jogador"));
            return;
        }

        Player player = Bukkit.getPlayer(sender.getName());
        if (!player.hasPermission("terrenos.fly") || !player.hasPermission("terrenos.admin")) {
            player.sendMessage(getCmTerrenos().getFileConfig().getMessage("Sem_Permissao"));
            return;
        }

        if (FlyTerreno.getIsFlying().contains(player.getName())) {
            player.sendMessage(getCmTerrenos().getFileConfig().getMessage("Fly_Desativado"));
            FlyTerreno.getIsFlying().remove(player.getName());
            player.setFlying(false);
            player.setAllowFlight(false);
            return;
        }

        HouseEntity house = Utilidades.getHomeLocation(player.getLocation().toVector());

        if (player.getLocation().toVector().isInAABB(house.getPositionMin(), house.getPositionMax())
                || !house.getDono().equalsIgnoreCase(player.getName())) {
            player.sendMessage(getCmTerrenos().getFileConfig().getMessage("Fora_Terreno_Fly"));
            return;
        }

        player.sendMessage(getCmTerrenos().getFileConfig().getMessage("Fly_Ativado"));
        FlyTerreno.getIsFlying().add(player.getName());
        player.setFlying(true);
        player.setAllowFlight(true);
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
