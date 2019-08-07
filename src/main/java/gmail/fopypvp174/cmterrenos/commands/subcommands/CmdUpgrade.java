package gmail.fopypvp174.cmterrenos.commands.subcommands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.api.Utilidades;
import gmail.fopypvp174.cmterrenos.entities.HouseEntity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class CmdUpgrade extends SubCommand {

    public CmdUpgrade(CmTerrenos cmTerrenos) {
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

        if (house.getNivelTerreno() == 10) {
            player.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Terreno_Nivel_Maximo"));
            return;
        }

        int priceUpgrade = getCmTerrenos().getFileConfig()
                .getPrecoNivel(house.getNivelTerreno() + 1);
        double playerMoney = getCmTerrenos().getEcon().getBalance(player);

        if (playerMoney < priceUpgrade) {
            player.sendMessage(getCmTerrenos().getFileConfig()
                    .getMessage("Sem_Dinheiro")
                    .replace("%m", String.valueOf(priceUpgrade)));
            return;
        }

        Vector[] position = Utilidades.calculePosition(house.getUpgradeMin()
                , house.getNivelTerreno() + 1);
        house.setPositionMin(position[0]);
        house.setPositionMax(position[1]);
        house.setNivelTerreno(house.getNivelTerreno() + 1);

        getCmTerrenos().getEcon().withdrawPlayer(player, priceUpgrade);

        Utilidades.setBlockAroundHome(house, new ItemStack(Material.FENCE));
        player.sendMessage(getCmTerrenos().getFileConfig()
                .getMessage("Terreno_Upado")
                .replace("%m", String.valueOf(priceUpgrade))
                .replace("%i", String.valueOf(house.getNivelTerreno() + 1)));
    }

    @Override
    public String name() {
        return "upgrade";
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[]{"upar"};
    }
}
