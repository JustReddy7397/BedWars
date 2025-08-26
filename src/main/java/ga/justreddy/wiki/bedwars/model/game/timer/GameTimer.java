package ga.justreddy.wiki.bedwars.model.game.timer;

import ga.justreddy.wiki.bedwars.BedWars;
import org.bukkit.Bukkit;

/**
 * @author JustReddy
 */
public abstract class GameTimer implements Runnable {

    private final int seconds;
    protected int ticksExceeded = 0;
    protected int taskId = -1;
    protected final BedWars plugin;
    protected boolean started = false;

    public GameTimer(int seconds, BedWars plugin) {
        this.seconds = seconds;
        this.plugin = plugin;
    }

    protected abstract void onStart();

    protected abstract void onTick();

    protected abstract void onEnd();

    @Override
    public void run() {
        if (!started) {
            throw new IllegalStateException("GameTimer has not been started. Use start() method to start the timer.");
        }

        if (ticksExceeded == 0) {
            onEnd();
            stop();
            return;
        }

        --ticksExceeded;
        onTick();
    }

    public void start() {
        ticksExceeded = seconds;
        taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 20L, 20L).getTaskId();
        started = true;
        onStart();
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskId);
        started = false;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getTicksExceeded() {
        return ticksExceeded;
    }

    public boolean isStarted() {
        return started;
    }
}
