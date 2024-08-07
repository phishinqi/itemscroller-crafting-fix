package fi.dy.masa.itemscroller.event;

import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import fi.dy.masa.itemscroller.config.Configs;
import fi.dy.masa.itemscroller.recipes.RecipeStorage;
import fi.dy.masa.itemscroller.villager.VillagerDataStorage;
import fi.dy.masa.malilib.interfaces.IWorldLoadListener;
import net.minecraft.registry.DynamicRegistryManager;

public class WorldLoadListener implements IWorldLoadListener
{
    @Override
    public void onWorldLoadPre(@Nullable ClientWorld worldBefore, @Nullable ClientWorld worldAfter, MinecraftClient mc)
    {
        // Quitting to main menu, save the settings before the integrated server gets shut down
        if (worldBefore != null && worldAfter == null)
        {
            this.writeData(worldBefore.getRegistryManager());
            VillagerDataStorage.getInstance().writeToDisk();
        }
    }

    @Override
    public void onWorldLoadPost(@Nullable ClientWorld worldBefore, @Nullable ClientWorld worldAfter, MinecraftClient mc)
    {
        // Logging in to a world, load the data
        if (worldBefore == null && worldAfter != null)
        {
            this.readStoredData(worldAfter.getRegistryManager());
            VillagerDataStorage.getInstance().readFromDisk();
        }
    }

    private void writeData(DynamicRegistryManager registryManager)
    {
        if (Configs.Generic.SCROLL_CRAFT_STORE_RECIPES_TO_FILE.getBooleanValue())
        {
            RecipeStorage.getInstance().writeToDisk(registryManager);
        }
    }

    private void readStoredData(DynamicRegistryManager registryManager)
    {
        if (Configs.Generic.SCROLL_CRAFT_STORE_RECIPES_TO_FILE.getBooleanValue())
        {
            RecipeStorage.getInstance().readFromDisk(registryManager);
        }
   }
}
