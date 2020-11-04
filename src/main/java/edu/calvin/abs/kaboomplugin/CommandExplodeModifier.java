package edu.calvin.abs.kaboomplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandExplodeModifier implements CommandExecutor {
    private static final char X = 'x',
                                Y = 'y',
                                Z = 'z';

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        // Check that we got a valid axis from the first argument.
        char axis = args[0].toLowerCase().charAt(0);
        if (axis != 'x' &&
            axis != 'y' &&
            axis != 'z') {
            return false;
        }

        // If the only argument is the axis, then print out the current modifier.
        if (args.length == 1) {
            switch (axis) {
                case X:
                    sender.sendMessage("Current " + axis + " modifier is " + KaboomPlugin.X_MODIFIER);
                    break;
                case Y:
                    sender.sendMessage("Current " + axis + " modifier is " + KaboomPlugin.Y_MODIFIER);
                    break;
                case Z:
                    sender.sendMessage("Current " + axis + " modifier is " + KaboomPlugin.Z_MODIFIER);
                    break;
                default:
                    break;
            }
            return true;
        }

        String modifierString = args[1];
        double modifier = 0.0;
        if (modifierString.equalsIgnoreCase("reset")) {
            // If the second argument is reset, then reset the modifier to default
            switch (axis) {
                case X:
                    modifier = KaboomPlugin.X_MODIFIER;
                    break;
                case Y:
                    modifier = KaboomPlugin.Y_MODIFIER;
                    break;
                case Z:
                    modifier = KaboomPlugin.Z_MODIFIER;
                    break;
                default:
                    break;
            }
        } else {
            // Try and parse the modifier from the second argument.
            try {
                modifier = Double.parseDouble(modifierString);
            } catch (NumberFormatException exception) {
                return false;
            }
        }

        // Set the modifier of the specified axis.
        switch (axis) {
            case X:
                KaboomPlugin.X_MODIFIER = modifier;
                break;
            case Y:
                KaboomPlugin.Y_MODIFIER = modifier;
                break;
            case Z:
                KaboomPlugin.Z_MODIFIER = modifier;
                break;
            default:
                break;
        }

        sender.sendMessage("Set the modifier for exploded block velocities on the " + axis + " to " + modifier);
        return true;
    }
}
