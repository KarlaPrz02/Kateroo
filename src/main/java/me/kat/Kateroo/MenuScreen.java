package me.kat.kateroo;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class MenuScreen extends Screen {

    private final Screen parent;

    // Screen menu
    public MenuScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        // Menu buttons

        // Toggle lava fog
        this.addDrawableChild(new ButtonWidget(
                this.width / 2 - 100,
                this.height / 4 + 40,
                200,
                20,
                new TranslatableText("menu.kateroo.toggle_lava_fog", ConfigHandler.isLavaFogEnabled() ? "ON" : "OFF"),
                button -> {
                    ConfigHandler.toggleLavaFog();
                    button.setMessage(new TranslatableText("menu.kateroo.toggle_lava_fog", ConfigHandler.isLavaFogEnabled() ? "ON" : "OFF"));
                }
        ));

        // Toggle BlockBreakingParticles
        this.addDrawableChild(new ButtonWidget(
                this.width / 2 - 100,
                this.height / 4 + 70,
                200,
                20,
                new TranslatableText("menu.kateroo.toggle_block_particles", ConfigHandler.isBlockParticlesEnabled() ? "ON" : "OFF"),
                button -> {
                    ConfigHandler.toggleBlockParticles();
                    button.setMessage(new TranslatableText("menu.kateroo.toggle_block_particles", ConfigHandler.isBlockParticlesEnabled() ? "ON" : "OFF"));
                }
        ));

        // Back to previous screen
        this.addDrawableChild(new ButtonWidget(
                this.width / 2 - 100,
                this.height / 4 + 100,
                200,
                20,
                new TranslatableText("menu.kateroo.back"),
                button -> {
                    this.client.setScreen(this.parent);
                }
        ));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // screen background
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPauseGame() {
        return false;
    }
}
