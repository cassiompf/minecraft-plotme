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

import java.util.ArrayList;

public class CmdCreate extends SubCommand {

    public CmdCreate(CmTerrenos cmTerrenos) {
        super(cmTerrenos);
    }

    public void onCommand(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 0) {
            sender.sendMessage(getCmTerrenos().getFileConfig().getMessage("Comando_Desconhecido"));
            return;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Somente_Jogador"));
            return;
        }
        Player player = Bukkit.getPlayer(sender.getName());
        if (!getCmTerrenos().getFileConfig().getWorldTerrain().equalsIgnoreCase(player.getLocation().getWorld().getName())) {
            player.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Mundo_Invalido"));
            return;
        }
        Integer priceUpgrade = getCmTerrenos().getFileConfig().getPrecoNivel(1);
        Double playerMoney = Double.valueOf(getCmTerrenos().getEcon().getBalance(player));

        if (playerMoney.doubleValue() < priceUpgrade.intValue()) {
            player.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Sem_Dinheiro").replace("%m", String.valueOf(priceUpgrade)));
            return;
        }
        if (super.getCmTerrenos().getHouseCache().hasTerreno(player.getName())) {
            player.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Ja_Tem_Terreno"));
            return;
        }
        if (Utilidades.getHomeLocation(player.getLocation().toVector()) != null) {
            player.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Terreno_Perto"));
            return;
        }

        Vector[] upgrade = Utilidades.calculeUpgrade(player.getLocation().toVector());
        Vector[] position = Utilidades.calculePosition(upgrade[0], 1);

        HouseEntity house = new HouseEntity();
        house.createPlayer(player, upgrade, position);

        getCmTerrenos().getHouseCache().setTerrain(house);

        new Thread(() ->  getCmTerrenos().getDatabaseDAO().setTerrain(house)).start();
        ItemStack itemStack = ItemBuilder.create(Material.CARPET, Byte.valueOf((byte) 14));
        Utilidades.setBlockAroundHome(house, itemStack);
        Utilidades.teleportHouse(player, house);
        player.sendMessage(super.getCmTerrenos().getFileConfig().getMessage("Terreno_Criado"));
    }


    @Override
    public String name() {
        return "criar";
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[]{"create"};
    }
}
