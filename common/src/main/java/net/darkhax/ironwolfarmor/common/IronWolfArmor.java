package net.darkhax.ironwolfarmor.common;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IronWolfArmor {
    public static final String MOD_ID = "ironwolfarmor";
    public static final String MOD_NAME = "Iron Wolf Armor";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}