package code.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "dealer_code", schema = "dbo")
@Getter
@Setter
public class DealerCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    @Column(name = "date_created")
    public LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(name = "date_modified")
    public LocalDateTime dateModified;

    @Column(name = "dealer_id")
    private Long dealerId;
    @Column(name = "year")
    private Short year;
    @Column(name = "last_code")
    private Long lastCode;

}
