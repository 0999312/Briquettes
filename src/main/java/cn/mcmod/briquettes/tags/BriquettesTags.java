package cn.mcmod.briquettes.tags;

import cn.mcmod_mmf.mmlib.utils.TagUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class BriquettesTags {
    public static final TagKey<Item> BAMBOO = TagUtils.forgeItemTag("bamboo");
    public static final TagKey<Item> SAWDUST = TagUtils.forgeItemTag("sawdust");
    public static final TagKey<Item> CHARCOAL = TagUtils.forgeItemTag("charcoal");
    public static final TagKey<Item> DUSTS_WOOD = TagUtils.forgeItemTag("dusts/wood");
    public static final TagKey<Item> DUSTS_CHARCOAL = TagUtils.forgeItemTag("dusts/charcoal");
    public static final TagKey<Item> DUSTS_COAL = TagUtils.forgeItemTag("dusts/coal");
}
