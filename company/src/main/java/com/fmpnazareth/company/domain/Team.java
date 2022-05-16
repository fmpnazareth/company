package com.fmpnazareth.company.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {
    private String id;
    private String name;
    private String teamLeadId;
    private List<String> teamMemberIds;
}
