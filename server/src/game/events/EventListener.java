package game.events;

public interface EventListener<T extends Event> {
  public void onEvent(T event);
}
