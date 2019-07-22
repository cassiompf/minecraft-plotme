package gmail.fopypvp174.cmterrenos.commands.subcommands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CmdHelp extends SubCommand {

    public CmdHelp(CmTerrenos cmTerrenos) {
        super(cmTerrenos);
    }

    @Override
    public void onCommand(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            for (String msg : getCmTerrenos().getFileConfig().getMessageList("Comando_Help")) {
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
