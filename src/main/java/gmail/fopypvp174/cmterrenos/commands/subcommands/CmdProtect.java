package gmail.fopypvp174.cmterrenos.commands.subcommands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.api.Utilidades;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CmdProtect extends SubCommand {

    public CmdProtect(CmTerrenos cmTerrenos) {
        super(cmTerrenos);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Somente_Jogador"));
            return;
        }

        Player player = Bukkit.getPlayer(sender.getName());
        if (!player.hasPermission("terrenos.admin")) {
            player.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Sem_Permissao_Staff"));
            return;
        }

        if (args.length > 0) {
            sender.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Comando_Desconhecido"));
            return;
        }

        if (!getCmTerrenos().getFileConfig().getWorldTerrain()
                .equalsIgnoreCase(player.getLocation().getWorld().getName())) {
            player.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Mundo_Invalido"));
            return;
        }

        HouseEntity houseLoc = Utilidades.getHomeLocation(player.getLocation().toVector());

        if (houseLoc != null) {
            player.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Nao_Pode_Proteger_Spawn_Aqui"));
            return;
        }

        Vector[] upgrade = Utilidades.calculeUpgrade(player.getLocation().toVector());

        HouseEntity house = new HouseEntity();
        house.createSpawnProtect(player, upgrade);

        long quantidade = getCmTerrenos().getHouseCache().amountTerrain(house.getDono());
        house.setHome((int) quantidade + 1);

        getCmTerrenos().getHouseCache().setTerrain(house);
        new Thread(() -> getCmTerrenos().getDatabaseDAO().setTerrain(house)).start();

        player.sendMessage(getCmTerrenos().getFileConfig()
                .getMessage("Spawn_Protegido"));
    }

    @Override
    public String name() {
        return "proteger";
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[]{"protect"};
    }
}
