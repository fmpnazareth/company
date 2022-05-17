package com.fmpnazareth.company.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "tb_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

}
