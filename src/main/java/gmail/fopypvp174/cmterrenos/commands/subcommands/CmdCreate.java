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

public class CmdCreate extends SubCommand {

    public CmdCreate(CmTerrenos cmTerrenos) {
        super(cmTerrenos);
    }

    public void onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getCmTerrenos().getFileConfig().getMessage("Somente_Jogador"));
            return;
        }

        if (args.length != 0) {
            sender.sendMessage(getCmTerrenos().getFileConfig().getMessage("Comando_Desconhecido"));
            return;
        }

        Player player = Bukkit.getPlayer(sender.getName());

        if (!getCmTerrenos().getFileConfig().getWorldTerrain().equalsIgnoreCase(player.getLocation().getWorld().getName())) {
            player.sendMessage(getCmTerrenos().getFileConfig().getMessage("Mundo_Invalido"));
            return;
        }

        if (Utilidades.getHomeLocation(player.getLocation().toVector()) != null) {
            player.sendMessage(getCmTerrenos().getFileConfig().getMessage("Terreno_Perto"));
            return;
        }

        long quantidade = getCmTerrenos().getHouseCache().amountTerrain(player.getName());

        if (quantidade == 2 && !player.hasPermission("terrenos.vip")) {
            player.sendMessage(getCmTerrenos().getFileConfig().getMessage("Qnt_Maxima_Terrenos"));
            return;
        }

        int priceCreate = getCmTerrenos().getFileConfig().getPrecoNivel(1);
        double playerMoney = getCmTerrenos().getEcon().getBalance(player);

        if (playerMoney < priceCreate) {
            player.sendMessage(getCmTerrenos().getFileConfig().getMessage("Sem_Dinheiro").replace("%m", String.valueOf(priceCreate)));
            return;
        }

        getCmTerrenos().getEcon().withdrawPlayer(player, priceCreate);
        Vector[] upgrade = Utilidades.calculeUpgrade(player.getLocation().toVector());
        Vector[] position = Utilidades.calculePosition(upgrade[0], 1);

        HouseEntity house = new HouseEntity();
        house.createPlayer(player, upgrade, position);
        house.setHome((int) quantidade + 1);

        getCmTerrenos().getHouseCache().setTerrain(house);

        new Thread(() -> getCmTerrenos().getDatabaseDAO().setTerrain(house)).start();

        Utilidades.setBlockAroundHome(house, new ItemStack(Material.FENCE));
        Utilidades.teleportHouse(player, house);
        player.sendMessage(getCmTerrenos().getFileConfig().getMessage("Terreno_Criado"));
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
