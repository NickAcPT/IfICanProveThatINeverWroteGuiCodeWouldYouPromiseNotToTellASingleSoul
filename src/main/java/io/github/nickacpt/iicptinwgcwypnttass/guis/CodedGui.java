package io.github.nickacpt.iicptinwgcwypnttass.guis;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import com.github.stefvanschie.inventoryframework.gui.type.util.MergedGui;
import com.github.stefvanschie.inventoryframework.pane.MasonryPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import io.github.nickacpt.iicptinwgcwypnttass.entries.GuiItemEntry;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public abstract class CodedGui {

    private Gui gui;

    @NotNull
    public abstract String getTitle();

    public void initGui(Gui gui) {

    }

    public int getRows() {
        return 1;
    }

    public final void init(Gui gui) {
        this.gui = gui;
        initGui(gui);
    }

    public final void initEntries(List<GuiItemEntry> entries) {
        for (var entry : entries) {
            if (gui instanceof MergedGui mergedGui) {
                GuiItem[] guiItems = entry.createGuiItems();

                int x = entry.getEntryLocation() != null ? entry.getEntryLocation().x() : Objects.requireNonNull(entry.getEntryRegion()).x();
                int y = entry.getEntryLocation() != null ? entry.getEntryLocation().y() : Objects.requireNonNull(entry.getEntryRegion()).y();
                var pane = new MasonryPane(
                        x - 1,
                        y - 1,
                        entry.getEntryRegion() != null ? entry.getEntryRegion().width() : 1,
                        entry.getEntryRegion() != null ? entry.getEntryRegion().height() : 1
                );

                for (GuiItem guiItem : guiItems) {
                    var staticPane = new StaticPane(1, 1);
                    staticPane.addItem(guiItem, 0, 0);
                    pane.addPane(staticPane);
                }
                mergedGui.addPane(pane);
            }
        }
    }
}
