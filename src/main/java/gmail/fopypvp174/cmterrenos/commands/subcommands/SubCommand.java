package gmail.fopypvp174.cmterrenos.commands.subcommands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public abstract class SubCommand {
    /*
    /command <sub-command> args[0] args[1]
     */
    private CmTerrenos cmTerrenos;

    public SubCommand(CmTerrenos cmTerrenos) {
        this.cmTerrenos = cmTerrenos;
    }

    public CmTerrenos getCmTerrenos() {
        return cmTerrenos;
    }

    public abstract void onCommand(CommandSender sender, ArrayList<String> args);

    public abstract String name();

    public abstract String info();

    public abstract String[] aliases();

}
