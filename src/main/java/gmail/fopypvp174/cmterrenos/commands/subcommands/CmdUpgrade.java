package gmail.fopypvp174.cmterrenos.commands.subcommands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.api.ItemBuilder;
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
            sender.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Somente_Jogador"));
            return;
        }
        Player player = Bukkit.getPlayer(sender.getName());

        if (!super.getCmTerrenos().getHouseCache().hasTerreno(player.getName())) {
            player.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Nao_Tem_Terreno"));
            return;
        }

        int nivelTerreno = super.getCmTerrenos().getHouseCache().getNivelTerreno(player.getName());

        if (nivelTerreno == 10) {
            player.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Terreno_Nivel_Maximo"));
            return;
        }

        Integer priceUpgrade = getCmTerrenos().getFileConfig().getPrecoNivel(nivelTerreno + 1);
        double playerMoney = getCmTerrenos().getEcon().getBalance(player);

        if (playerMoney < priceUpgrade) {
            player.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Sem_Dinheiro").replace("%m", String.valueOf(priceUpgrade)));
            return;
        }

        HouseEntity house = super.getCmTerrenos().getHouseCache().getHouse(player.getName());

        Vector[] position = Utilidades.calculePosition(house.getUpgradeMin(), nivelTerreno + 1);
        house.setPositionMin(position[0]);
        house.setPositionMax(position[1]);
        house.setNivelTerreno(nivelTerreno + 1);

        getCmTerrenos().getEcon().withdrawPlayer(player, priceUpgrade);

        ItemStack itemStack = ItemBuilder.create(Material.FENCE, (byte) 14);
        Utilidades.setBlockAroundHome(house, itemStack);

        player.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Terreno_Upado").replace("%m", String.valueOf(priceUpgrade)).replace("%i", String.valueOf(nivelTerreno + 1)));
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
