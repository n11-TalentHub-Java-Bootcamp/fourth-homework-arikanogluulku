package com.n11bootcamp.fourthhomework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.n11bootcamp.fourthhomework.entity.enumeration.DebtType;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "debt")
@Data
public class Debt implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_debt" , allocationSize = 1 , initialValue = 100 )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator ="seq_debt" )
    private Long id;

    @Column(name="main_debt_amount" , updatable = false)
    private BigDecimal mainDebtAmount;

    private BigDecimal remainingDebt;

    @Enumerated(EnumType.STRING)
    private DebtType debtType;

    private Long userId;

    private Long connectedDebt;

    private LocalDate expiryDate;




}
