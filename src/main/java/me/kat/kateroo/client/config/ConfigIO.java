package me.kat.kateroo.client.config;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.StringNbtReader;

import java.io.*;
import java.util.function.Consumer;

public class ConfigIO {

    private static final File saveFile = new File("config/kateroo/config.dat");
    private static NbtCompound config = new NbtCompound();

    public static void saveConfig(NbtCompound tag) {
        String stringConfig = tag.toString();

        synchronized (saveFile) {
            try {
                if (!saveFile.exists()) {
                    saveFile.getParentFile().mkdirs();
                    saveFile.createNewFile();
                    if (!saveFile.exists()) {
                        return;
                    }
                }

                FileWriter writer = new FileWriter(saveFile);
                writer.write(stringConfig);
                writer.close();

            } catch (IOException e) {
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

                config = StringNbtReader.parse(new StringReader(sb.toString()));
            } catch (IOException | CommandSyntaxException e) {
                config = new NbtCompound();
            }
        }
    }

    public static NbtCompound getConfig() {
        return config;
    }

    public static void getAndSaveConfig(Consumer<NbtCompound> action) {
        action.accept(config);
        saveConfig(config);
    }

}

