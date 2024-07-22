
package softeer.demo.controller;

import softeer.demo.dto.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import softeer.demo.service.EventService;



@Controller
public class EventController {


    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService=eventService;
    }


    //이벤트 값 설정
    @PostMapping("/setEvent")
    public void setEvent(@ModelAttribute EventDTO eventDTO)
    {

    }

}

