package com.fmpnazareth.company.config;

import com.fmpnazareth.company.external.ExternalCompanyClient;
import com.fmpnazareth.company.repository.TeamUserRoleRepository;
import com.fmpnazareth.company.repository.domain.TeamUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class InitialBaseConfig {

    @Autowired
    private ExternalCompanyClient externalCompanyClient;

    @Autowired
    private TeamUserRoleRepository teamUserRoleRepository;

    @PostConstruct
    public void initialize(){
        initializeUsersWithTeam();
        initializeUsersWithoutTeam();
    }

    public void initializeUsersWithTeam() {
        externalCompanyClient.getTeams().stream()
                .map(team -> externalCompanyClient.getTeam(team.getId()))
                .filter(Optional::isPresent)
                .filter(team -> team.get().getTeamMemberIds() != null)
                .forEach(team -> {
                    team.get().getTeamMemberIds().stream().forEach(user -> {
                        teamUserRoleRepository.save(TeamUserRole
                                .builder()
                                .teamId(team.get().getId())
                                .userId(user)
                                .build());
                    });
                });
    }

    private void initializeUsersWithoutTeam() {
        externalCompanyClient.getUsers().stream().forEach(user -> {
            Example<TeamUserRole> teamUserRoleExample = Example
                    .of(TeamUserRole
                            .builder()
                            .userId(user.getId())
                            .build());
            List<TeamUserRole> userTeams = teamUserRoleRepository.findAll(teamUserRoleExample);

            if(userTeams.isEmpty()){
                teamUserRoleRepository.save(TeamUserRole
                        .builder()
                        .userId(user.getId())
                        .build());
            }
        });
    }
}
