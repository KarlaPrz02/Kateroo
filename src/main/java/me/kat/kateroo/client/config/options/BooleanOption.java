package me.kat.kateroo.client.config.options;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.nbt.CompoundTag;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class BooleanOption extends BaseOption<Boolean> {

    public BooleanOption(String name, boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    protected BiConsumer<String, Boolean> getParser(CompoundTag config) {
        return config::putBoolean;
    }

    @Override
    protected Function<String, Boolean> getReader(CompoundTag config) {
        return config::getBoolean;
    }

    @Override
    public AbstractConfigListEntry<Boolean> asConfigEntry(ConfigBuilder builder) {
        return builder.entryBuilder()
                .startBooleanToggle(this.getTitle(), this.getValue())
                .setDefaultValue(this.getDefaultValue())
                .setTooltip(this.getTooltip())
                .setSaveConsumer(this::setValue)
                .build();
    }


}
