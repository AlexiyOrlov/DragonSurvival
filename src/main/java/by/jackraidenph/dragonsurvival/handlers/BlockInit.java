package by.jackraidenph.dragonsurvival.handlers;

import by.jackraidenph.dragonsurvival.DragonSurvivalMod;
import by.jackraidenph.dragonsurvival.blocks.DragonAltarBlock;
import by.jackraidenph.dragonsurvival.blocks.nest.FillerNestBlock;
import by.jackraidenph.dragonsurvival.blocks.nest.MasterNestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@ObjectHolder(DragonSurvivalMod.MODID)
@Mod.EventBusSubscriber(modid = DragonSurvivalMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit {
    public static final Block dragon_altar = new DragonAltarBlock(
            Block.Properties
                    .create(Material.ANVIL)
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(2)
                    .hardnessAndResistance(5.0f)
                    .lightValue(5)
                    .sound(SoundType.ANVIL));

    public static final Block master_nest = new MasterNestBlock();
    public static final Block filler_nest = new FillerNestBlock();

    public static TileEntityType<MasterNestBlock.TileMasterNest> master_nest_tile;
    public static TileEntityType<FillerNestBlock.TileFillerNest> filler_nest_tile;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        event.getRegistry().register(dragon_altar);
        event.getRegistry().register(master_nest);
        event.getRegistry().register(filler_nest);
    }

    @SubscribeEvent
    public static void registerTiles(final RegistryEvent.Register<TileEntityType<?>> event) {
        master_nest_tile = registerTile(event, MasterNestBlock.TileMasterNest::new, master_nest);
        filler_nest_tile = registerTile(event, FillerNestBlock.TileFillerNest::new, filler_nest);
    }

    private static <A extends TileEntity> TileEntityType<A> registerTile(RegistryEvent.Register<TileEntityType<?>> event, Supplier<A> factory, Block block) {
        TileEntityType<A> tile = TileEntityType.Builder.create(factory, block).build(null);
        tile.setRegistryName(block.getRegistryName());
        event.getRegistry().register(tile);
        return tile;
    }

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        registerItemBlock(event, dragon_altar);
        registerItemBlock(event, master_nest);
        registerItemBlock(event, filler_nest);
    }

    private static void registerItemBlock(RegistryEvent.Register<Item> event, Block block) {
        event.getRegistry().register(new BlockItem(block, new Item.Properties().group(DragonSurvivalMod.tab)).setRegistryName(block.getRegistryName()));
    }
}