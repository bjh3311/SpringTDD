package softeer.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import softeer.demo.dto.EventDTO;
import softeer.demo.model.Event;
import softeer.demo.service.EventService;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)//Post와 Get메소드를 받는지 테스트 하기위한 어노테이션
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc; //HTTP요청을 시뮬레이션한다

    @MockBean // EventService를 모킹하여 EventControlle가 실제 서비스 구현 대신 모킹된 객체를 사용하게
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    private EventDTO eventDTO;
    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //MockitoAnnotations.openMocks(this)를 호출하여 Mockito 어노테이션을 활성화합니다.
        eventDTO= new EventDTO(1L,"첫번째 이벤트","선착순 이벤트입니다",
                "2021-01-01T15:39:30","2021-01-01T16:39:30",100);
        event = eventDTO.toEntity();
    }

    @DisplayName("EventService의 createEvent가 정확히 한 번 호출되었는지 확인하는 테스트")
    @Test
    void createEventTest() throws Exception{



    }

    @Test
    void deleteEvent() {

    }


}