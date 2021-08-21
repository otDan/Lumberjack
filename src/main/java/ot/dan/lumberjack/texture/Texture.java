package ot.dan.lumberjack.texture;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Texture {

    private final @NotNull ItemStack textureItem;

    public Texture(int customModelData) {
        this.textureItem = new ItemStack(Material.LEATHER_HORSE_ARMOR);
        ItemMeta itemMeta = this.textureItem.getItemMeta();
        assert itemMeta != null;
        itemMeta.setCustomModelData(customModelData);
        this.textureItem.setItemMeta(itemMeta);
    }

    public ItemStack getTextureItem() {
        return textureItem.clone();
    }
}
