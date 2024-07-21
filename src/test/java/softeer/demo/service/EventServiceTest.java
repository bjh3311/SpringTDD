package softeer.demo.service;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import softeer.demo.model.Event;
import softeer.demo.repository.EventRepository;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event event;



    //각 테스트가 실행되기전에 실행된다
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        //MockitoAnnotations.openMocks(this)를 호출하여 Mockito 어노테이션을 활성화합니다.
        event= new Event(1L,"첫번째 이벤트","선착순 이벤트입니다",
                LocalDateTime.parse("2021-01-01T15:39:30"),LocalDateTime.parse("2021-01-01T16:39:30"),100);
    }//given

    @DisplayName("이벤트 생성: 저장된 이벤트의 속성을 검증하고 save 메소드 호출 횟수를 확인")
    @Test
    void createEventTest() {

        //given
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        //when
        Event createdEvent = eventService.createEvent(event);

        //then
        verify(eventRepository,times(1)).save(any(Event.class));
        //eventRepository의 save 메소드가 정확히 한 번 쓰였는가?

        assertThat(createdEvent).isNotNull();
        assertThat(createdEvent.getId()).isEqualTo(event.getId());
        assertThat(createdEvent.getName()).isEqualTo(event.getName());
        assertThat(createdEvent.getDescription()).isEqualTo(event.getDescription());
    }

    @DisplayName("이벤트 삭제: deleteById 메소드 호출 횟수 확인")
    @Test
    void deleteEventTest() {
        //given
        Long eventId = 1L;
        doNothing().when(eventRepository).deleteById(1L);
        //eventRepository의 deleteById가 실행되었을때 아무것도 실행하지말라

        // when
        eventService.deleteEvent(eventId);

        // then
        verify(eventRepository, times(1)).deleteById(eventId);

        //호출되는지만 확인
        //이미 eventRepository.deleteById(eventId)는 테스트를 통과하였다.
    }

    @DisplayName("이벤트 시작 시간 설정: 시작 시간 업데이트 및 findById, save 메소드 호출 횟수 검증")
    @Test
    void setStartTimeTest() {

        //given
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(event));

        //when
        eventService.createEvent(event);
        eventService.setStartTime(1L,LocalDateTime.parse("2021-03-05T15:39:30"));
        Event findedEvent = eventRepository.findById(1L).get();

        //then
        verify(eventRepository,times(2)).findById(1L);
        verify(eventRepository,times(2)).save(event);
        assertThat(findedEvent.getStartTime()).isEqualTo(LocalDateTime.parse("2021-03-05T15:39:30"));


    }
    @DisplayName("이벤트 종료 시간 설정: 종료 시간 업데이트 및 findById, save 메소드 호출 횟수 검증")
    @Test
    void setEndTimeTest() {
        //given
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(event));

        //when
        eventService.createEvent(event);
        eventService.setEndTime(1L,LocalDateTime.parse("2021-03-06T15:39:30"));
        Event findedEvent = eventRepository.findById(1L).get();

        //then
        verify(eventRepository,times(2)).findById(1L);
        verify(eventRepository,times(2)).save(event);
        assertThat(findedEvent.getEndTime()).isEqualTo(LocalDateTime.parse("2021-03-06T15:39:30"));
    }

    @DisplayName("이벤트 당첨자 수 설정: 당첨자 수 업데이트 및 findById, save 메소드 호출 횟수 검증")
    @Test
    void setNumberOfWinnerTest() {
        //given
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(event));

        //when
        eventService.createEvent(event);
        eventService.setNumberOfWinner(1L,300);
        Event findedEvent = eventRepository.findById(1L).get();

        //then
        verify(eventRepository,times(2)).findById(1L);
        verify(eventRepository,times(2)).save(event);
        assertThat(findedEvent.getNumberOfWinners()).isEqualTo(300);
    }
}