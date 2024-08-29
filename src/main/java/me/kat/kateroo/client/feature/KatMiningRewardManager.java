package me.kat.kateroo.client.feature;

import com.mojang.blaze3d.systems.RenderSystem;
import me.kat.kateroo.client.config.KaterooModMenu;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KatMiningRewardManager {

    /*
        No mires este código.
        En serio.
        Quiérete un poco.
     */

    public static final int MINED_BLOCKS_PER_EVENT = 30000;
    public static final Identifier HEART = new Identifier("kateroo", "textures/widget/kat_heart.png");

    private static final List<String> messages = new ArrayList<>();

    private static int ticks = 0;

    private static String currentMessage = "";
    private static int currentMinedCount = 0;
    private static float currentProgress = 200;

    private static int minedDivisionCount = -1;
    private static int lastMinedDivisionCount = -1;

    public static void render(MinecraftClient client, int blitOffset) {
        if (!KaterooModMenu.miningRewardDrops.getValue()) return;
        if (currentProgress > 200) return;

        float tickDelta = client.getTickDelta();
        float animation = (ticks + tickDelta) * 0.025f;

        client.getTextureManager().bindTexture(HEART);

        double scale = client.getWindow().getScaledHeight() / 1080.0 * 1.5;

        RenderSystem.pushMatrix();
        RenderSystem.translatef(client.getWindow().getScaledWidth() / 2.0f, (float) (100 * scale), 0);
        RenderSystem.enableBlend();
        RenderSystem.scaled(scale, scale, 1);

        RenderSystem.translated(0, -200 + client.getWindow().getHeight() / scale * (currentProgress + tickDelta) / 200, -5);

        RenderSystem.rotatef((float) Math.sin(animation * 1.6) * 35, 0, 0, 1);

        DrawableHelper.blit(-45, -40, blitOffset, 0, 0, 90, 80, 90, 90);

        RenderSystem.scaled(1.5, 1.5, 1);

        String message = currentMessage;
        int textWidth = client.textRenderer.getStringWidth(message);

        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.5f, 0.5f, 0);
        client.textRenderer.drawWithShadow(message, -textWidth / 2f, 45, 0x07000b);
        RenderSystem.popMatrix();

        RenderSystem.pushMatrix();
        RenderSystem.translatef(-0.5f, -0.5f, 0);
        client.textRenderer.drawWithShadow(message, -textWidth / 2f, 45, 0x07000b);
        RenderSystem.popMatrix();

        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.5f, -0.5f, 0);
        client.textRenderer.drawWithShadow(message, -textWidth / 2f, 45, 0x07000b);
        RenderSystem.popMatrix();

        RenderSystem.pushMatrix();
        RenderSystem.translatef(-0.5f, 0.5f, 0);
        client.textRenderer.drawWithShadow(message, -textWidth / 2f, 45, 0x07000b);
        RenderSystem.popMatrix();



        client.textRenderer.drawWithShadow(message, -textWidth / 2f, 45, 0xFFFFFF);

        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
    }

    public static String getRandomMessage() {
        if (messages.isEmpty()) return "Guapa :3";

        return formatString(messages.get((int) (Math.random() * messages.size())));
    }

    public static String formatString(String message) {
        // Hora real formateada en 24 horas (20:13)
        String realTime = new SimpleDateFormat("HH:mm").format(new Date());

        return message
                .replace("%playerName%", MinecraftClient.getInstance().getSession().getUsername())
                .replace("%daytime%", realTime)
                .replace("%minedCount%", String.valueOf(currentMinedCount));
    }

    public static int getMinedBlocks() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return 0;

        return player.getStatHandler().getStat(Stats.USED.getOrCreateStat(Items.DIAMOND_PICKAXE));
    }

    public static void triggerEvent(ClientPlayerEntity player) {
        currentMessage = getRandomMessage();
        currentMinedCount = getMinedBlocks();
        currentProgress = 0;

        player.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.VOICE,1f, 1f);
    }

    public static void registerListeners() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!KaterooModMenu.miningRewardDrops.getValue()) return;

            ClientPlayerEntity player = client.player;
            if (player == null) return;

            ticks++;
            currentProgress += 1;

            if (lastMinedDivisionCount != minedDivisionCount) {
                if (lastMinedDivisionCount >= 0 && minedDivisionCount > lastMinedDivisionCount) {
                    triggerEvent(player);
                }
                lastMinedDivisionCount = minedDivisionCount;
            }
            if (ticks % 10 == 0) {
                ClientPlayNetworkHandler handler = client.getNetworkHandler();
                if (handler != null) {
                    client.getNetworkHandler().sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.REQUEST_STATS));
                }

                minedDivisionCount = (int) Math.floor(getMinedBlocks() / (double) MINED_BLOCKS_PER_EVENT);
            }
        });
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier("kateroo", "kat_mining_reward_manager");
            }

            @Override
            public void apply(ResourceManager manager) {
                messages.clear();

                manager.findResources("splash", path -> path.endsWith("mining_rewards.txt")).forEach(resource -> {
                    try {
                        InputStream inputStream = manager.getResource(resource).getInputStream();

                        // Crear un array de strings, donde cada string es una línea del archivo
                        List<String> lines = IOUtils.readLines(inputStream, "UTF-8");
                        messages.addAll(lines);

                        inputStream.close();
                    } catch (Exception ignored) {

                    }
                });
            }
        });
    }

}
