package softeer.demo.service;

import io.netty.handler.ssl.OpenSslSessionTicketKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softeer.demo.entity.Ticket;
import softeer.demo.repository.TicketRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MySQLTicketService {

    private final TicketRepository ticketRepository;

    public void deleteAll(){
        ticketRepository.deleteAll();
    }

    public void save(Ticket ticket){
        ticketRepository.save(ticket);
    }

    public Ticket findById(Long id){
        return ticketRepository.findById(id).orElse(new Ticket());
    }


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
