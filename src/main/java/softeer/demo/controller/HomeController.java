package softeer.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softeer.demo.dto.EventDTO;


@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model)
    {

        model.addAttribute("eventDTO",new EventDTO());
        return "index";
    }
}
