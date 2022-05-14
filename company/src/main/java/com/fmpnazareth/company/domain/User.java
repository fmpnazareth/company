package com.fmpnazareth.company.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String avatarUrl;
    private String location;
}
