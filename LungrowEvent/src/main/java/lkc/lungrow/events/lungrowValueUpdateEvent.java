package lkc.lungrow.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;


/**
 * lungrowValueUpdateEvent 입니다.
 * MQTT 값이 전송되어 Inhale value와 exhale value가 업데이트 되었을 때 호출됩니다.
 */
public class lungrowValueUpdateEvent extends Event implements Cancellable {
    private boolean cancelled = false;
    private static final HandlerList handlers = new HandlerList();
    private String message;
    private int inhale = 0;
    private int exhale = 0;


    /**
     * @param message 전달받는 테스트 인자입니다.
     */
    public lungrowValueUpdateEvent(String message) {
        this.message = message;
        Bukkit.getServer().getLogger().info("lungrowEventEnable");
    }

    /**
     * @return message를 돌려줍니다.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @param inhale 이벤트를 통해 전달 할 inhale value를 업데이트 합니다.
     * @param exhale 이벤트를 통해 전달 할 exhale value를 업데이트 합니다.
     */
    public void updateValue(int inhale, int exhale){
        this.inhale = inhale;
        this.exhale = exhale;
    }

    /**
     * @return 이벤트에 저장된 inhale값을 돌려줍니다.
     */
    public int getInhale(){
        return inhale;
    }

    /**
     * @return 이벤트에 저장된 exhale값을 돌려줍니다.
     */
    public int getExhale(){
        return exhale;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancelled;
    }
}

