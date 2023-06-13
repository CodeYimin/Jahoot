package game.events;

/**
 * Represents an event listener.
 * 
 * @param <T> The type of event.
 */
public interface EventListener<T extends Event> {
  /**
   * Handles an event.
   * 
   * @param event The event.
   */
  public void onEvent(T event);
}
