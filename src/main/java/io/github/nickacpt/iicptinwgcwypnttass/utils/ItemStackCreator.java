package io.github.nickacpt.iicptinwgcwypnttass.utils;

import io.github.nickacpt.iicptinwgcwypnttass.annotations.ItemName;
import io.github.nickacpt.iicptinwgcwypnttass.entries.GuiItemEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;

public enum ItemStackCreator {
    INSTANCE;

    public ItemStack createItemStackForEntry(GuiItemEntry entry) {
        var method = entry.getMember();
        var item = new ItemStack(entry.getEntryItem().value(), entry.getEntryItem().amount());
        var entryItemName = method.getAnnotation(ItemName.class);
        if (entryItemName != null) {
            item.editMeta(m -> m.displayName(Component.text(entryItemName.value()).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)));
        }
        return item;
    }
}
