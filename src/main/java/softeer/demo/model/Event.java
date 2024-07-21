package softeer.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
public class Event {

    @Id
    private Long id;

    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int numberOfWinners;

    public Event() {
    }

    @Builder
    public Event(Long id, String name, String description, LocalDateTime startTime, LocalDateTime endTime, int numberOfWinners) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfWinners = numberOfWinners;
    }
}
