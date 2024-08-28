package me.kat.kateroo.client.config;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;

import java.io.*;
import java.util.function.Consumer;

public class ConfigIO {

    private static final File saveFile = new File("config/kateroo/config.dat");
    private static CompoundTag config = new CompoundTag();

    public static boolean saveConfig(CompoundTag tag) {
        String stringConfig = tag.toString();

        synchronized (saveFile) {
            try {
                if (!saveFile.exists() && (!saveFile.getParentFile().mkdirs() || !saveFile.createNewFile())) {
                    throw new IOException("Unable to create config save data file");
                }

                FileWriter writer = new FileWriter(saveFile);
                writer.write(stringConfig);
                writer.close();

                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

    public static void loadConfig() {
        synchronized (saveFile) {
            try (FileReader reader = new FileReader(saveFile)) {
                StringBuilder sb = new StringBuilder();
                int i;
                while ((i = reader.read()) != -1) {
                    sb.append((char) i);
                }

                config = new StringNbtReader(new StringReader(sb.toString())).parseCompoundTag();
            } catch (IOException | CommandSyntaxException e) {
                config = new CompoundTag();
            }
        }
    }

    public static CompoundTag getConfig() {
        return config;
    }

    public static void getAndSaveConfig(Consumer<CompoundTag> action) {
        action.accept(config);
        saveConfig(config);
    }

}
