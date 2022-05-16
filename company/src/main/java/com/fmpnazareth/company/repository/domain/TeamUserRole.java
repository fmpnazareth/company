package com.fmpnazareth.company.repository.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_user_team_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    private String teamId;
    private Integer roleId;
}
