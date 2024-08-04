package softeer.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @NotNull
    private Integer ticket;

    public void decrease(){
        if(this.ticket-1 <0){
            throw new RuntimeException("선착순 이벤트가 마감되었습니다." );
        }
        System.out.println("잔여 티켓 : " + --this.ticket);
    }
}
