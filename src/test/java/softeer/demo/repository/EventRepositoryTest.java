package softeer.demo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import softeer.demo.model.Event;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    private Event event;

    @BeforeEach
    void setUp() {
        event = Event.builder()
                .id(1L)
                .name("첫번째 이벤트")
                .description("선착순 이벤트입니다")
                .startTime(LocalDateTime.parse("2021-01-01T15:39:30"))
                .endTime(LocalDateTime.parse("2021-01-31T16:39:30"))
                .numberOfWinners(100)
                .build();
    }

    @DisplayName("이벤트 저장: 저장된 이벤트의 속성을 검증")
    @Test
    public void saveTest() {
        //given

        //when
        final Event result = eventRepository.save(event);
        //Then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("첫번째 이벤트");
        assertThat(result.getDescription()).isEqualTo("선착순 이벤트입니다");
        assertThat(result.getStartTime()).isEqualTo(LocalDateTime.parse("2021-01-01T15:39:30"));
        assertThat(result.getEndTime()).isEqualTo(LocalDateTime.parse("2021-01-31T16:39:30"));
        assertThat(result.getNumberOfWinners()).isEqualTo(100);
    }


    @DisplayName("이벤트 삭제: ID로 이벤트 삭제 후 존재 여부 확인")
    @Test
    public void deleteByIdTest() {
        //given
        eventRepository.save(event);

        //when
        eventRepository.deleteById(1L);

        //then
        assertThat(eventRepository.findById(1L)).isEmpty();
    }

    @DisplayName("이벤트 시작 시간 설정: 시작 시간 업데이트 및 검증")
    @Test
    public void setStartTimeTest() {
        //given
        eventRepository.save(event);

        //when
        eventRepository.findById(1L).ifPresent(i -> {
            i.setStartTime(LocalDateTime.parse("2022-03-03T15:39:30"));
        });

        //then
        assertThat(eventRepository.findById(1L).get().getStartTime())
                .isEqualTo(LocalDateTime.parse("2022-03-03T15:39:30"));
    }

    @DisplayName("이벤트 종료 시간 설정: 종료 시간 업데이트 및 검증")
    @Test
    public void setEndTimeTest() {
        //given
        eventRepository.save(event);

        //when

        eventRepository.findById(1L).ifPresent(i -> {
            i.setEndTime(LocalDateTime.parse("2022-03-03T15:39:30"));
        });

        //then
        assertThat(eventRepository.findById(1L).get().getEndTime())
                .isEqualTo(LocalDateTime.parse("2022-03-03T15:39:30"));
    }

    @DisplayName("이벤트 당첨자 수 설정: 당첨자 수 업데이트 및 검증")
    @Test
    public void setNumberOfWinners() {
        //given
        eventRepository.save(event);

        //when

        eventRepository.findById(1L).ifPresent(i -> {
            i.setNumberOfWinners(300);
            });

        //then
        assertThat(eventRepository.findById(1L).get().getNumberOfWinners())
                .isEqualTo(300);
    }
}