package io.github.nickacpt.iicptinwgcwypnttass.entries;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.Item;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.Location;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.Region;
import io.github.nickacpt.iicptinwgcwypnttass.guis.CodedGui;
import io.github.nickacpt.iicptinwgcwypnttass.guis.impl.CodedGuiCreator;
import io.github.nickacpt.iicptinwgcwypnttass.utils.ItemStackCreator;
import java.lang.reflect.AccessibleObject;
import java.util.Objects;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GuiItemEntry {
    protected final Item item;
    protected final CodedGui gui;
    @Nullable
    protected final Location location;
    @Nullable
    private final Region region;
    private final AccessibleObject member;

    public GuiItemEntry(CodedGui gui, AccessibleObject member) {
        item = Objects.requireNonNull(member.getAnnotation(Item.class),
                "Method/Field needs to be annotated with @Item for it to be a MethodGuiItemEntry");
        this.gui = gui;
        region = member.getAnnotation(Region.class);
        location = member.getAnnotation(Location.class);

        if (location == null && region == null) {
            throw new NullPointerException("Method/Field needs to be annotated with @Location/@Region for it to be a MethodGuiItemEntry");
        }
        this.member = member;
    }

    public Item getEntryItem() {
        return item;
    }

    @Nullable
    public Location getEntryLocation() {
        return location;
    }

    @Nullable
    public Region getEntryRegion() {
        return region;
    }

    public final GuiItem[] createGuiItems() {
        @NotNull ItemStack[] itemStacks = getEntryItemStacks();
        var finalArray = new GuiItem[itemStacks.length];

        for (int i = 0, itemStacksLength = itemStacks.length; i < itemStacksLength; i++) {
            ItemStack stack = itemStacks[i];
            GuiItem guiItem = (new GuiItem(stack, e -> {
                e.setCancelled(true);
                onClick(e, stack);
            }));
            finalArray[i] = guiItem;
        }
        return finalArray;
    }

    @NotNull
    protected ItemStack[] getEntryItemStacks() {
        return new ItemStack[]{ItemStackCreator.INSTANCE.createItemStackForEntry(this)};
    }

    protected void handleClickResult(InventoryClickEvent e, Object result) {
        if (result == null) {
            // Either returns void or is null - Close gui
            e.getWhoClicked().closeInventory();
        } else if (result != gui && CodedGui.class.isAssignableFrom(result.getClass())) {
            // Returns another gui - Open it
            CodedGuiCreator.INSTANCE.createGui((CodedGui) result).show(e.getWhoClicked());
        }
    }

    protected abstract void onClick(InventoryClickEvent e, ItemStack stack);

    public AccessibleObject getMember() {
        return member;
    }
}
