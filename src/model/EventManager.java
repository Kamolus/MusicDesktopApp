package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventManager extends Employee {

    private String areaOfOperation;
    private List<Event> events = new ArrayList<>();

    public EventManager(String name, String email, LocalDate hireDate, double salary, String areaOfOperation) {
        super(name, email, hireDate, salary);
        try{
            setAreaOfOperation(areaOfOperation);
        }catch (Exception e){
            e.printStackTrace();
            removeFromExtent();
        }
    }

    public String getAreaOfOperation() {
        return areaOfOperation;
    }

    public void setAreaOfOperation(String areaOfOperation) {
        if(areaOfOperation == null || areaOfOperation.isBlank()) {
            throw new IllegalArgumentException("Area of operation cannot be null or empty");
        }
        this.areaOfOperation = areaOfOperation;
    }

    public void addEvent(Event event) {
        if(!events.contains(event) && event != null) {
            event.setEventManager(this);
            events.add(event);
        }
    }

    public void removeEvent(Event event) {
        if(events.remove(event)) {
            event.setEventManager(null);
        }
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }


    @Override
    public String toString() {
        return "EventManager{" +
                "events=" + events +
                '}';
    }
}
