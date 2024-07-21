package softeer.demo.dto;

import lombok.Data;
import org.springframework.cglib.core.Local;
import softeer.demo.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Data
public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private int numberOfWinners;

    public EventDTO() {

    }

    public EventDTO(Long id, String name, String description, String startTime, String endTime, int numberOfWinners) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfWinners = numberOfWinners;
    }

    public Event toEntity() {
        LocalDateTime newStartTime = LocalDateTime.parse(this.startTime, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime newEndTime = LocalDateTime.parse(this.endTime,DateTimeFormatter.ISO_DATE_TIME);
        return Event.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .startTime(newStartTime)
                .endTime(newEndTime)
                .numberOfWinners(this.numberOfWinners)
                .build();
    }
}