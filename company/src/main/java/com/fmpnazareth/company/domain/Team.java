package com.fmpnazareth.company.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "tb_team")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {
    @Id
    private String id;
    private String name;
    private String teamLeadId;
//    private List<String> teamMemberIds;
}
