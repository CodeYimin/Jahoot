package game.events;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {
  private HashMap<Class<? extends Event>, ArrayList<EventListener<? extends Event>>> listeners = new HashMap<>();

  public <T extends Event> void emitEvent(Class<T> eventClass, T event) {
    ArrayList<EventListener<? extends Event>> eventListeners = (ArrayList<EventListener<? extends Event>>) listeners
        .get(eventClass);
    if (eventListeners != null) {
      for (EventListener<? extends Event> listener : eventListeners) {
        @SuppressWarnings("unchecked")
        EventListener<T> eventListener = (EventListener<T>) listener;
        eventListener.onEvent(event);
      }
    }
  }

  public <T extends Event> void addListener(Class<T> eventClass, EventListener<T> listener) {
    ArrayList<EventListener<? extends Event>> eventListeners = (ArrayList<EventListener<? extends Event>>) listeners
        .get(eventClass);
    if (eventListeners == null) {
      eventListeners = new ArrayList<EventListener<? extends Event>>();
      listeners.put(eventClass, eventListeners);
    }

    eventListeners.add(listener);
  }

  public <T extends Event> void removeListener(Class<T> eventClass, EventListener<T> listener) {
    ArrayList<EventListener<? extends Event>> eventListeners = (ArrayList<EventListener<? extends Event>>) listeners
        .get(eventClass);
    if (eventListeners == null) {
      return;
    }

    eventListeners.remove(listener);
  }
}
