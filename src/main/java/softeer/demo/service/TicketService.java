package softeer.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softeer.demo.entity.Ticket;
import softeer.demo.repository.TicketRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;

    public void decreaseTicket(Long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket Not Found."));
        ticket.decrease();
        ticketRepository.save(ticket);
    }

    public void decreaseTicketWithLock(Long ticketId){
        Ticket ticket = ticketRepository.findByIdWithLock(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket Not Found."));
        ticket.decrease();
        ticketRepository.save(ticket);

    }

}
