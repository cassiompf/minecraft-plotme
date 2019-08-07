package gmail.fopypvp174.cmterrenos.commands.subcommands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHelp extends SubCommand {

    public CmdHelp(CmTerrenos cmTerrenos) {
        super(cmTerrenos);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            String msg = getCmTerrenos().getFileConfig()
                    .getMessageList("Comando_Help");
            sender.sendMessage(msg);
            if (sender.hasPermission("terrenos.admin")) {
                msg = getCmTerrenos().getFileConfig()
                        .getMessageList("Comando_Help_Admin");
                sender.sendMessage(msg);
            }
        }
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[]{"ajuda"};
    }
}
