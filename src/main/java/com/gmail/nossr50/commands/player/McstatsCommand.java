package com.gmail.nossr50.commands.player;

import com.gmail.nossr50.config.Config;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.util.commands.CommandUtils;
import com.gmail.nossr50.util.player.UserManager;
import com.gmail.nossr50.util.scoreboards.ScoreboardManager;
import com.google.common.collect.ImmutableList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class McstatsCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (CommandUtils.noConsoleUsage(sender)) {
            return true;
        }

        if (!CommandUtils.hasPlayerDataKey(sender)) {
            return true;
        }

        if (args.length == 0) {
            if (UserManager.getPlayer((Player) sender) == null) {
                sender.sendMessage(LocaleLoader.getString("Profile.PendingLoad"));
                return true;
            }

            Player player = (Player) sender;

            if (Config.getInstance().getStatsUseBoard() && Config.getInstance().getScoreboardsEnabled()) {
                ScoreboardManager.enablePlayerStatsScoreboard(player);

                if (!Config.getInstance().getStatsUseChat()) {
                    return true;
                }
            }

            player.sendMessage(LocaleLoader.getString("Stats.Own.Stats"));
            player.sendMessage(LocaleLoader.getString("mcMMO.NoSkillNote"));

            CommandUtils.printGatheringSkills(player);
            CommandUtils.printCombatSkills(player);
            CommandUtils.printMiscSkills(player);

            int powerLevelCap = Config.getInstance().getPowerLevelCap();

            if (powerLevelCap != Integer.MAX_VALUE) {
                player.sendMessage(LocaleLoader.getString("Commands.PowerLevel.Capped", UserManager.getPlayer(player).getPowerLevel(), powerLevelCap));
            } else {
                player.sendMessage(LocaleLoader.getString("Commands.PowerLevel", UserManager.getPlayer(player).getPowerLevel()));
            }

            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        return ImmutableList.of();
    }
}
