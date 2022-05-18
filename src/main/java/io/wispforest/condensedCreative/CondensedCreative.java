package io.wispforest.condensedCreative;

import io.wispforest.condensedCreative.compat.CondensedCreativeConfig;
import io.wispforest.condensedCreative.compat.owo.OwoCompat;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CondensedCreative implements ModInitializer, ClientModInitializer {

    public static final String MODID = "condensed_creative";

    public static Identifier createID(String path){
        return new Identifier(MODID, path);
    }

    public static ItemGroup testGroup = null;
    public static Supplier<ItemGroup> createOwoItemGroup = () -> null;

    public static Predicate<ItemGroup> isOwoItemGroup = itemGroup -> false;
    public static Function<ItemGroup, Integer> getTabIndexFromOwoGroup = o -> -1;

    public static ConfigHolder<CondensedCreativeConfig> MAIN_CONFIG;

    //---------------------------------------------------------------------------------------------------------

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("owo")) {
            OwoCompat.init();
        }

        testGroup = createOwoItemGroup.get();

        AutoConfig.register(CondensedCreativeConfig.class, GsonConfigSerializer::new);

        MAIN_CONFIG = AutoConfig.getConfigHolder(CondensedCreativeConfig.class);
    }

    //---------------------------------------------------------------------------------------------------------

    @Override
    public void onInitializeClient() {
        if(CondensedCreative.isDeveloperMode()) {
            CondensedEntryRegistry.fromTag(CondensedCreative.createID("test1"), Blocks.OAK_LOG, ItemTags.LOGS)
                    .addItemGroup(ItemGroup.BUILDING_BLOCKS)
                    .setTitleStringFromTagKey();

            if(CondensedCreative.testGroup != null) {
                CondensedEntryRegistry.fromTag(CondensedCreative.createID("test2"), Blocks.OAK_LOG, ItemTags.LOGS)
                        .addItemGroup(CondensedCreative.testGroup, 0)
                        .setTitleStringFromTagKey();

                CondensedEntryRegistry.fromTag(CondensedCreative.createID("test3"), Blocks.WHITE_CARPET, ItemTags.CARPETS)
                        .addItemGroup(CondensedCreative.testGroup, 1)
                        .setTitleStringFromTagKey();
            }
        }
    }

    //---------------------------------------------------------------------------------------------------------

    public static boolean isDeveloperMode(){
        return true;
    }
}
