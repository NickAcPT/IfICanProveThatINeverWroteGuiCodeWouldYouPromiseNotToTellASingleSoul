package io.github.nickacpt.iicptinwgcwypnttass.guis.impl;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.Item;
import io.github.nickacpt.iicptinwgcwypnttass.annotations.gui.types.ChestCodedGui;
import io.github.nickacpt.iicptinwgcwypnttass.entries.FieldGuiItemEntry;
import io.github.nickacpt.iicptinwgcwypnttass.entries.GuiItemEntry;
import io.github.nickacpt.iicptinwgcwypnttass.entries.MethodGuiItemEntry;
import io.github.nickacpt.iicptinwgcwypnttass.guis.CodedGui;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.NotImplementedException;

public enum CodedGuiCreator {
    INSTANCE;

    public Gui createGui(CodedGui gui) {
        var baseGui = createBaseGui(gui);
        gui.initEntries(createEntries(gui));
        return baseGui;
    }

    private List<GuiItemEntry> createEntries(CodedGui gui) {
        var entries = new ArrayList<GuiItemEntry>();
        var fields = gui.getClass().getFields();
        var methods = gui.getClass().getMethods();
        for (var field : fields) {
            var entryItemAnnotation = field.getAnnotation(Item.class);
            if (entryItemAnnotation == null) continue;
            entries.add(new FieldGuiItemEntry(gui, field));
        }
        for (var method : methods) {
            var entryItemAnnotation = method.getAnnotation(Item.class);
            if (entryItemAnnotation == null) continue;
            entries.add(new MethodGuiItemEntry(gui, method));
        }

        return entries;
    }

    private Gui createBaseGui(CodedGui gui) {
        var annotations = gui.getClass().getDeclaredAnnotations();
        for (var annotation : annotations) {
            if (annotation instanceof ChestCodedGui) {
                var chestGui = new ChestGui(gui.getRows(), gui.getTitle());
                gui.init(chestGui);
                return chestGui;
            }
        }

        throw new NotImplementedException();
    }

}
