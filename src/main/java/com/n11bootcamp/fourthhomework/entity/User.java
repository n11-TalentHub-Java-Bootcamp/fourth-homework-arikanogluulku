package com.n11bootcamp.fourthhomework.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="`user`")
@Data
public class User implements Serializable {
    @Id
    @SequenceGenerator(name = "seq_user" , allocationSize = 1 , initialValue = 100 )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator ="seq_user" )
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String username;
}
