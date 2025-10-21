package org.LukDT.bank_application_rest_api.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "operations")
@NoArgsConstructor
@Getter
@Setter()
public class Operation {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "type_operation")
    private int typeOperation;
    @Column
    private BigDecimal summa;
    @Column(name = "date_type_operation")
    private LocalDateTime dateOperation;

    public Operation(User user, int typeOperation, BigDecimal summa, LocalDateTime dateOperation) {
        this.user = user;
        this.typeOperation = typeOperation;
        this.summa = summa;
        this.dateOperation = dateOperation;
    }
}
