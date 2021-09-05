package io.github.nickacpt.iicptinwgcwypnttass.entries;

import io.github.nickacpt.iicptinwgcwypnttass.guis.CodedGui;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FieldGuiItemEntry extends GuiItemEntry {
    private final boolean requiresInstanceForGetter;
    private MethodHandle fieldGetterHandle = null;

    public FieldGuiItemEntry(CodedGui gui, Field member) {
        super(gui, member);
        member.setAccessible(true);
        requiresInstanceForGetter = !Modifier.isStatic(member.getModifiers());
        try {
            fieldGetterHandle = MethodHandles.lookup().unreflectGetter(member);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onClick(InventoryClickEvent e, ItemStack stack) {
        try {
            Object result = getFieldValue();
            handleClickResult(e, result);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private Object getFieldValue() {
        try {
            return requiresInstanceForGetter ? fieldGetterHandle.invoke(gui) : fieldGetterHandle.invoke();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected @NotNull ItemStack[] getEntryItemStacks() {
        Object fieldValue = getFieldValue();
        if (fieldValue instanceof ItemStack item) {
            return new ItemStack[]{item};
        }
        return super.getEntryItemStacks();
    }
}
