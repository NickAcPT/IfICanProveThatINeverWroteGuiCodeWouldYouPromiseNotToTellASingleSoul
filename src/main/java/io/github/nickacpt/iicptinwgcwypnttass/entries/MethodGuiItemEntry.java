package io.github.nickacpt.iicptinwgcwypnttass.entries;

import io.github.nickacpt.iicptinwgcwypnttass.annotations.UsesExternalItemStack;
import io.github.nickacpt.iicptinwgcwypnttass.guis.CodedGui;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MethodGuiItemEntry extends GuiItemEntry {
    private final Method method;
    private MethodHandle methodHandle = null;
    private MethodHandle itemGetterHandle = null;
    private boolean itemGetterIsList = false;

    public MethodGuiItemEntry(CodedGui gui, @NotNull Method method) {
        super(gui, method);
        this.method = method;

        try {
            method.setAccessible(true);
            methodHandle = MethodHandles.lookup().unreflect(method);

            prepareItemField(gui, method);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void prepareItemField(CodedGui gui, @NotNull Method method) throws IllegalAccessException {
        if (method.isAnnotationPresent(UsesExternalItemStack.class)) {
            try {
                itemGetterHandle = MethodHandles.lookup().findGetter(
                        gui.getClass(),
                        method.getName() + "Item",
                        ItemStack.class
                );
                itemGetterIsList = false;
            } catch (NoSuchFieldException e) {
                try {
                    itemGetterHandle = MethodHandles.lookup().findGetter(
                            gui.getClass(),
                            method.getName() + "Item",
                            ItemStack[].class
                    );
                    itemGetterIsList = true;
                } catch (NoSuchFieldException noSuchFieldException) {
                    noSuchFieldException.printStackTrace();
                }
            }
        }
    }

    @Override
    protected @NotNull ItemStack[] getEntryItemStacks() {
        if (itemGetterHandle != null) {
            try {
                Object itemFieldResult = itemGetterHandle.invoke(gui);

                if (itemGetterIsList) return (ItemStack[]) itemFieldResult;
                return new ItemStack[]{(ItemStack) itemFieldResult};
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return super.getEntryItemStacks();
    }

    @Override
    protected void onClick(InventoryClickEvent e, ItemStack stack) {
        try {
            Object[] args = computeArgumentsToPass(e, stack);
            var result = methodHandle.asSpreader(Object[].class, args.length).invoke(args);
            handleClickResult(e, result);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private Object[] computeArgumentsToPass(InventoryClickEvent event, ItemStack stack) {
        List<Object> argumentsToPass = new ArrayList<>();
        if (!Modifier.isStatic(method.getModifiers())) {
            // Method is not static, pass gui instance
            argumentsToPass.add(gui);
        }

        for (Class<?> parameterType : method.getParameterTypes()) {
            if (Player.class.isAssignableFrom(parameterType)) {
                argumentsToPass.add(event.getWhoClicked());
            } else if (ItemStack.class.isAssignableFrom(parameterType)) {
                argumentsToPass.add(stack);
            } else if (InventoryClickEvent.class.isAssignableFrom(parameterType)) {
                argumentsToPass.add(event);
            }

        }
        return argumentsToPass.toArray(Object[]::new);
    }
}
