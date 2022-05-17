package com.fmpnazareth.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String avatarUrl;
    private String location;
}
