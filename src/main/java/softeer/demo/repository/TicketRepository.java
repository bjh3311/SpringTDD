package softeer.demo.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softeer.demo.entity.Ticket;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE) // 비관적 락
    @Query(value = "select * from Ticket where id = :id" , nativeQuery = true)
    Optional<Ticket> findByIdWithLock(@Param("id") Long id);
}
