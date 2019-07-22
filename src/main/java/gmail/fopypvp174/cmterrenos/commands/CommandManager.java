package gmail.fopypvp174.cmterrenos.commands;

import gmail.fopypvp174.cmterrenos.CmTerrenos;
import gmail.fopypvp174.cmterrenos.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

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
        this.commands.add(new CmdCoord(cmTerrenos));
        this.commands.add(new CmdHelp(cmTerrenos));
        this.commands.add(new CmdFly(cmTerrenos));
        this.commands.add(new CmdTp(cmTerrenos));
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

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(args));

        if (arrayList.size() > 0) {
            arrayList.remove(0);
        }
        try {
            subCommand.onCommand(sender, arrayList);
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

            int length = aliases.length;

            for (int var5 = 0; var5 < length; var5++) {
                String alias = aliases[var5];

                if (name.equalsIgnoreCase(alias)) {
                    return sc;
                }
            }
        }
        return null;
    }
}
