package cn.mcmod.briquettes.data;

import org.jetbrains.annotations.Nullable;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BriquettesBlockTagsProvider extends BlockTagsProvider {

    public BriquettesBlockTagsProvider(DataGenerator datagen, String modId,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(datagen, modId, existingFileHelper);
    }
    
    @Override
    protected void addTags() {

    }

}
