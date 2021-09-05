package io.github.nickacpt.iicptinwgcwypnttass.testplugin;

import com.destroystokyo.paper.profile.PlayerProfile;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.Item;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.ItemName;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.Location;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.gui.types.ChestCodedGui;
import io.github.nickacpt.iicptinwgcwypnttass.guis.CodedGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

@ChestCodedGui
public class TestGui extends CodedGui {

    private final String reportedPlayer;

    public TestGui(String reportedPlayer) {
        this.reportedPlayer = reportedPlayer;
        reportedPlayerHead = createPlayerHead();
    }

    private ItemStack createPlayerHead() {
        ItemStack headItem = new ItemStack(Material.PLAYER_HEAD);
        headItem.editMeta(m -> {
            PlayerProfile reportedPlayerProfile = Bukkit.createProfile(reportedPlayer);
            reportedPlayerProfile.complete(true, true);
            ((SkullMeta) m).setPlayerProfile(reportedPlayerProfile);
        });

        return headItem;
    }

    @Item
    @Location(x = 5, y = 2)
    public final ItemStack reportedPlayerHead;

    @Item(value = Material.GREEN_WOOL)
    @ItemName("\u00A7aConfirm Report")
    @Location(x = 4, y = 4)
    public void confirmReport(Player player) {
        player.sendMessage(
                Component.text("You have successfully reported " + reportedPlayer, NamedTextColor.GREEN)
        );
    }

    @Item(value = Material.RED_WOOL)
    @ItemName("\u00A7cCancel Report")
    @Location(x = 6, y = 4)
    public void cancelReport(Player player) {
        player.sendMessage(
                Component.text("You have cancelled the report of " + reportedPlayer, NamedTextColor.RED)
        );
    }

    @Override
    public int getRows() {
        return 5;
    }

    @Override
    public @NotNull String getTitle() {
        return "Report - " + reportedPlayer;
    }
}




