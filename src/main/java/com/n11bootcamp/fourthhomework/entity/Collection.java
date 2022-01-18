package com.n11bootcamp.fourthhomework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.n11bootcamp.fourthhomework.entity.enumeration.DebtType;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "collection")
@Data
public class Collection {
    @Id
    @SequenceGenerator(name = "seq_collection" , allocationSize = 1 , initialValue = 100 )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator ="seq_collection" )
    private Long id;

    private LocalDate dateOfRegistration;

    private Long debtId;

    @Enumerated(EnumType.STRING)
    private DebtType debtType;

    private Long connectedDebtId;

    private BigDecimal collectionAmount;
}
