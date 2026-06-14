package net.darkhax.ironwolfarmor.common;

import net.darkhax.bookshelf.common.api.registry.ContentProvider;
import net.darkhax.bookshelf.common.impl.registry.adapter.CreativeModeTabAdapter;
import net.darkhax.bookshelf.common.impl.registry.adapter.ItemRegistryAdapter;
import net.minecraft.tags.ItemTags;

public class Content implements ContentProvider {

    public static final WolfArmorMaterial LEATHER = WolfArmorMaterial.create("leather", 3, 0, 0, 15, 5, ItemTags.REPAIRS_LEATHER_ARMOR);
    public static final WolfArmorMaterial COPPER = WolfArmorMaterial.create("copper", 6, 0, 0, 8, 11, ItemTags.REPAIRS_COPPER_ARMOR);
    public static final WolfArmorMaterial CHAIN = WolfArmorMaterial.create("chainmail", 10, 0, 0, 12, 15, ItemTags.REPAIRS_CHAIN_ARMOR);
    public static final WolfArmorMaterial IRON = WolfArmorMaterial.create("iron", 11, 0, 0, 9, 15, ItemTags.REPAIRS_IRON_ARMOR);
    public static final WolfArmorMaterial GOLD = WolfArmorMaterial.create("gold", 6, 0, 0, 25, 7, ItemTags.REPAIRS_GOLD_ARMOR);
    public static final WolfArmorMaterial DIAMOND = WolfArmorMaterial.create("diamond", 15, 2, 0, 10, 33, ItemTags.REPAIRS_DIAMOND_ARMOR);
    public static final WolfArmorMaterial NETHERITE = WolfArmorMaterial.create("netherite", 19, 3, 1, 15, 37, ItemTags.REPAIRS_NETHERITE_ARMOR);


    @Override
    public void defineItems(ItemRegistryAdapter registry) {
        for (WolfArmorMaterial material : WolfArmorMaterial.materials().values()) {
            registry.addSimple(material.name + "_wolf_armor", material::configureItem);
        }
    }

    @Override
    public void defineCreativeTabs(CreativeModeTabAdapter registry) {
        registry.add("tab", () -> IRON.item.get().getDefaultInstance(), (_, output) -> WolfArmorMaterial.materials().values().forEach(type -> output.accept(type.item.get())));
    }

    @Override
    public String namespace() {
        return IronWolfArmor.MOD_ID;
    }
}