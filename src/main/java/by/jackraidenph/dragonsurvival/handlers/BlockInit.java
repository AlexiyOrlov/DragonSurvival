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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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

    public static final Block master_nest_small_water = new MasterNestBlock(new AxisAlignedBB(
            new BlockPos(0, 0, 0),
            new BlockPos(0, 0, 0)), 100)
            .setRegistryName("master_nest_small_water");

    public static final Block master_nest_middle_water = new MasterNestBlock(new AxisAlignedBB(
            new BlockPos(-1, 0, -1),
            new BlockPos(1, 0, 1)), 100)
            .setRegistryName("master_nest_middle_water");

    public static final Block master_nest_large_water = new MasterNestBlock(new AxisAlignedBB(
            new BlockPos(-2, 0, -2),
            new BlockPos(2, 1, 2)), 100)
            .setRegistryName("master_nest_large_water");

    public static final Block filler_nest = new FillerNestBlock();

    public static TileEntityType<MasterNestBlock.TileMasterNest> master_nest_tile;
    public static TileEntityType<FillerNestBlock.TileFillerNest> filler_nest_tile;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        event.getRegistry().register(dragon_altar);
        event.getRegistry().register(master_nest_small_water);
        event.getRegistry().register(master_nest_middle_water);
        event.getRegistry().register(master_nest_large_water);
        event.getRegistry().register(filler_nest);
    }

    @SubscribeEvent
    public static void registerTiles(final RegistryEvent.Register<TileEntityType<?>> event) {
        master_nest_tile = registerTile(event, MasterNestBlock.TileMasterNest::new, new ResourceLocation(DragonSurvivalMod.MODID, "master_nest"), master_nest_small_water, master_nest_middle_water, master_nest_large_water);
        filler_nest_tile = registerTile(event, FillerNestBlock.TileFillerNest::new, filler_nest);
    }

    private static <A extends TileEntity> TileEntityType<A> registerTile(RegistryEvent.Register<TileEntityType<?>> event, Supplier<A> factory, Block block) {
        return registerTile(event, factory, block.getRegistryName(), block);
    }

    private static <A extends TileEntity> TileEntityType<A> registerTile(RegistryEvent.Register<TileEntityType<?>> event, Supplier<A> factory, ResourceLocation registryName, Block... validBlocks) {
        TileEntityType<A> tile = TileEntityType.Builder.create(factory, validBlocks).build(null);
        tile.setRegistryName(registryName);
        event.getRegistry().register(tile);
        return tile;
    }

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        registerItemBlock(event, dragon_altar);
        registerItemBlock(event, master_nest_small_water);
        registerItemBlock(event, master_nest_middle_water);
        registerItemBlock(event, master_nest_large_water);
        registerItemBlock(event, filler_nest);
    }

    private static void registerItemBlock(RegistryEvent.Register<Item> event, Block block) {
        event.getRegistry().register(new BlockItem(block, new Item.Properties().group(DragonSurvivalMod.tab)).setRegistryName(block.getRegistryName()));
    }
}