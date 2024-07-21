
package softeer.demo.service;

import softeer.demo.dto.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softeer.demo.model.Event;
import softeer.demo.repository.EventRepository;

import java.time.LocalDateTime;

@Service
public class EventService {


    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository=eventRepository;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public void setStartTime(Long id, LocalDateTime time) {
        eventRepository.findById(id).ifPresent(event -> {
            event.setStartTime(time);
            eventRepository.save(event);
        });
    }

    public void setEndTime(Long id, LocalDateTime time) {
        eventRepository.findById(id).ifPresent(event -> {
            event.setEndTime(time);
            eventRepository.save(event);
        });
    }

    public void setNumberOfWinner(Long id,int num) {
        eventRepository.findById(id).ifPresent(event -> {
            event.setNumberOfWinners(num);
            eventRepository.save(event);
        });
    }


}

