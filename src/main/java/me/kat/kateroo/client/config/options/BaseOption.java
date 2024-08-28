package me.kat.kateroo.client.config.options;

import me.kat.kateroo.client.config.ConfigIO;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class BaseOption<T> {

    private final String name;
    private final Text title;
    private final Text tooltip;

    private final T defaultValue;
    private T value;

    protected BaseOption(String name, T defaultValue) {
        this.defaultValue = defaultValue;

        this.name = name;
        this.title = new TranslatableText("option.kateroo." + name);
        this.tooltip = new TranslatableText("option.kateroo." + name + ".tooltip");

        if (ConfigIO.getConfig().contains(name)) {
            this.value = this.getReader(ConfigIO.getConfig()).apply(this.name);
        } else {
            this.setValue(defaultValue);
        }
    }

    public void setValue(T value) {
        this.value = value;
        ConfigIO.getAndSaveConfig(config -> this.getParser(config).accept(this.name, value));
    }

    public T getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title.asString();
    }

    public String getTooltip() {
        return this.tooltip.asString();
    }

    public String getName() {
        return this.name;
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    protected abstract BiConsumer<String, T> getParser(CompoundTag config);
    protected abstract Function<String, T> getReader(CompoundTag config);
    public    abstract AbstractConfigListEntry<T> asConfigEntry(ConfigBuilder builder);

}
