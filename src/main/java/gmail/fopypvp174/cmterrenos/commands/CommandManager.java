package gmail.fopypvp174.cmterrenos.commands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CommandManager implements CommandExecutor {

    private Set<SubCommand> commands = new HashSet<>();
    private CmTerrenos cmTerrenos;
    //Sub commands

    public CommandManager(CmTerrenos cmTerrenos) {
        this.cmTerrenos = cmTerrenos;
    }

    public void setup() {
        cmTerrenos.getCommand("terrenos").setExecutor(this);

        this.commands.add(new CmdCreate(cmTerrenos));
        this.commands.add(new CmdHelp(cmTerrenos));
        this.commands.add(new CmdFly(cmTerrenos));
        this.commands.add(new CmdTp(cmTerrenos));
        this.commands.add(new CmdFriend(cmTerrenos));
        this.commands.add(new CmdUpgrade(cmTerrenos));
        this.commands.add(new CmdProtect(cmTerrenos));
        this.commands.add(new CmdDelete(cmTerrenos));
        this.commands.add(new CmdConvert(cmTerrenos));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(cmTerrenos.getFileConfig().getMessage("Comando_Desconhecido"));
            return true;
        }
        SubCommand subCommand = get(args[0]);

        if (subCommand == null) {
            sender.sendMessage(cmTerrenos.getFileConfig().getMessage("Comando_Desconhecido"));
            return true;
        }

        String[] newArgs = new String[args.length - 1];

        for (int i = 0; i < newArgs.length; i++) {
            newArgs[i] = args[i + 1];
        }
        try {
            subCommand.onCommand(sender, newArgs);
        } catch (Exception ex) {
            sender.sendMessage(cmTerrenos.getFileConfig().getMessage("Erro_Executar_Comando"));
            ex.printStackTrace();
        }
        return false;
    }

    private SubCommand get(String name) {
        Iterator<SubCommand> subCommands = this.commands.iterator();

        while (subCommands.hasNext()) {
            SubCommand sc = subCommands.next();

            if (sc.name().equalsIgnoreCase(name)) {
                return sc;
            }
            String[] aliases = sc.aliases();

            for (int var5 = 0; var5 < aliases.length; var5++) {
                String alias = aliases[var5];

                if (name.equalsIgnoreCase(alias)) {
                    return sc;
                }
            }
        }
        return null;
    }
}
