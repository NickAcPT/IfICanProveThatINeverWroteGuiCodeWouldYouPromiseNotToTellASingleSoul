package io.github.nickacpt.iicptinwgcwypnttass.testplugin;

import io.github.nickacpt.iicptinwgcwypnttass.guis.impl.CodedGuiCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class TestPlugin extends JavaPlugin {

    public TestPlugin() {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equals("testgui")) {
            if (!(sender instanceof Player player)) return false;
            var gui = CodedGuiCreator.INSTANCE.createGui(new TestGui(player.getName()));
            gui.show(player);
            return true;
        } else if (command.getName().equals("testgui2")) {
            if (!(sender instanceof Player player)) return false;
            var gui = CodedGuiCreator.INSTANCE.createGui(new ReportListGui());
            gui.show(player);
            return true;
        }
        return super.onCommand(sender, command, label, args);
    }
}
