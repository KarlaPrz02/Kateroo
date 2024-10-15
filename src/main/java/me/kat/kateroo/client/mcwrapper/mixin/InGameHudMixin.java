package me.kat.kateroo.client.mcwrapper.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.kat.kateroo.client.config.KaterooModMenu;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "renderScoreboardSidebar", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", shift = At.Shift.BEFORE))
    private void addTotal(ScoreboardObjective scoreboardObjective, CallbackInfo ci, @Local @NotNull List<ScoreboardPlayerScore> list) {
        int total = 0;
        for (ScoreboardPlayerScore score : list) {
            total += score.getScore();
        }

        ScoreboardPlayerScore totalScore = new ScoreboardPlayerScore(scoreboardObjective.getScoreboard(), scoreboardObjective, "Total");
        totalScore.setScore(total);
        list.add(totalScore);
    }
}


