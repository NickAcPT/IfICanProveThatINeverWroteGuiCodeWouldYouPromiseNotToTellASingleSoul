package io.github.nickacpt.iicptinwgcwypnttass.testplugin;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.collect.Lists;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.Item;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.Location;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.Region;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.UsesExternalItemStack;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.gui.types.ChestCodedGui;
import io.github.nickacpt.iicptinwgcwypnttass.guis.CodedGui;
import java.util.Objects;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

@ChestCodedGui
public class ReportListGui extends CodedGui {
    public ItemStack closeItem;
    public ItemStack[] playerHeadItem;

    public ReportListGui() {
        closeItem = new ItemStack(Material.BARRIER);

        playerHeadItem = Lists.newArrayList("NickAc", "MTM123", "Notch").stream()
                .map(p -> {
                    PlayerProfile profile = Bukkit.createProfile(p);
                    profile.complete(true, true);
                    return profile;
                })
                .map(p -> {
                    ItemStack headItem = new ItemStack(Material.PLAYER_HEAD);
                    headItem.editMeta(m -> {
                        ((SkullMeta) m).setPlayerProfile(p);
                        m.displayName(Component.text(Objects.requireNonNull(p.getName()))
                                .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
                        m.lore(Lists.newArrayList(
                                Component.text().decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                                        .color(NamedTextColor.WHITE).append(Component.text(
                                                "Click here to report " + Objects.requireNonNull(p.getName())))
                                        .build()));
                    });

                    return headItem;
                }).toArray(ItemStack[]::new);
    }

    @Item
    @Region(y = 2, width = 2, height = 3)
    @UsesExternalItemStack
    public TestGui playerHead(Player p, ItemStack stack, InventoryClickEvent e) {
        return new TestGui(Objects.requireNonNull(((SkullMeta) stack.getItemMeta()).getPlayerProfile()).getName());
    }

    @Item
    @Location(x = 5, y = 5)
    @UsesExternalItemStack
    public void close() {

    }

    @Override
    public @NotNull String getTitle() {
        return "List of Players to Report";
    }

    @Override
    public int getRows() {
        return 5;
    }
}


