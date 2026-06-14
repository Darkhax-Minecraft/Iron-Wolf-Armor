package net.darkhax.ironwolfarmor.common;

import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.pricklemc.common.api.annotations.RangedInt;
import net.darkhax.pricklemc.common.api.annotations.Value;
import net.darkhax.pricklemc.common.api.config.ConfigManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class WolfArmorMaterial {

    private static final Map<String, WolfArmorMaterial> MATERIALS = new LinkedHashMap<>();
    private static final Map<String, WolfArmorMaterial> UNMODIFIABLE = Collections.unmodifiableMap(MATERIALS);

    public final String name;
    public final TagKey<Item> repairItem;
    public final CachedSupplier<Item> item = CachedSupplier.cache(this::findItem);

    @Value(comment = "The amount of armor points provided by the armor.")
    @RangedInt(min = 0)
    public int armor;

    @Value(comment = "The amount of armor toughness provided by the armor.")
    @RangedInt(min = 0)
    public int toughness;

    @Value(comment = "The amount of knockback resistance provided by the armor.")
    @RangedInt(min = 0)
    public int knockback_resistance;

    @Value(comment = "The quality of enchantments received when enchanting the item. Higher is better.")
    @RangedInt(min = 0)
    public int enchantability;

    @Value(comment = "The durability value for the armor. Setting the durability to 0 will make the armor unbreakable.")
    @RangedInt(min = 0)
    public int durability;

    private WolfArmorMaterial(String name, int armor, int toughness, int knockback_resistance, int enchantability, int durability, TagKey<Item> repair) {
        this.name = name;
        this.armor = armor;
        this.toughness = toughness;
        this.knockback_resistance = knockback_resistance;
        this.enchantability = enchantability;
        this.durability = durability;
        this.repairItem = repair;
    }

    private Item findItem() {
        return BuiltInRegistries.ITEM.getValue(IronWolfArmor.id(this.name + "_wolf_armor"));
    }

    public Item.Properties configureItem(Item.Properties properties) {
        properties = properties.wolfArmor(new ArmorMaterial(this.durability, Map.of(ArmorType.BODY, this.armor), this.enchantability, SoundEvents.ARMOR_EQUIP_WOLF, this.toughness, this.knockback_resistance * 0.1f, this.repairItem, ResourceKey.create(EquipmentAssets.ROOT_ID, IronWolfArmor.id(this.name))));
        if (this.durability > 0) {
            properties = properties.durability(this.durability);
        }
        else {
            properties = properties.component(DataComponents.UNBREAKABLE, Unit.INSTANCE);
        }
        if ("chainmail".equals(this.name)) {
            properties = properties.rarity(Rarity.UNCOMMON);
        }
        else if ("netherite".equals(this.name)) {
            properties = properties.fireResistant();
        }
        return properties;
    }

    public static Map<String, WolfArmorMaterial> materials() {
        return UNMODIFIABLE;
    }

    public static WolfArmorMaterial create(String materialName, int armor, int toughness, int knockback_resistance, int enchantability, int durability, TagKey<Item> repair) {
        WolfArmorMaterial config = new WolfArmorMaterial(materialName, armor, toughness, knockback_resistance, enchantability, ArmorType.BODY.getDurability(durability), repair);
        config = ConfigManager.load(IronWolfArmor.MOD_ID + "/materials/" + materialName, config);
        MATERIALS.put(materialName, config);
        return config;
    }
}