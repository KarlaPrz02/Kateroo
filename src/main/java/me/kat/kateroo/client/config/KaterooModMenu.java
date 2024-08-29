package me.kat.kateroo.client.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.kat.kateroo.client.config.options.BooleanOption;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;

public class KaterooModMenu implements ModMenuApi {

    private static final String RENDERING_CATEGORY_TITLE = "category.kateroo.rendering";

    public static final BooleanOption noLavaFog                        = new BooleanOption("noLavaFog", false);
    public static final BooleanOption disableBlockBreakingParticles    = new BooleanOption("disableBlockBreakingParticles", false);
    public static final BooleanOption disableCameraMovementWhenDamage  = new BooleanOption("disableCameraMovementWhenDamage", false);
    public static final BooleanOption disableFireOnCam                 = new BooleanOption("disableFireOnCam", false);
    public static final BooleanOption miningRewardDrops                = new BooleanOption("miningRewardDrops", false);

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create();
            ConfigCategory renderingCategory = builder.getOrCreateCategory(RENDERING_CATEGORY_TITLE);

            renderingCategory.addEntry(noLavaFog.asConfigEntry(builder));
            renderingCategory.addEntry(disableBlockBreakingParticles.asConfigEntry(builder));
            renderingCategory.addEntry(disableCameraMovementWhenDamage.asConfigEntry(builder));
            renderingCategory.addEntry(disableFireOnCam.asConfigEntry(builder));
            renderingCategory.addEntry(miningRewardDrops.asConfigEntry(builder));

            return builder.build();
        };
    }
}
