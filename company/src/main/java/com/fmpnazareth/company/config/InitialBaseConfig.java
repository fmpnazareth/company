package com.fmpnazareth.company.config;

import com.fmpnazareth.company.external.ExternalCompanyClient;
import com.fmpnazareth.company.domain.repository.MembershipRepository;
import com.fmpnazareth.company.domain.Membership;
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
    private MembershipRepository membershipRepository;

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
                        membershipRepository.save(Membership
                                .builder()
                                .teamId(team.get().getId())
                                .userId(user)
                                .build());
                    });
                });
    }

    private void initializeUsersWithoutTeam() {
        externalCompanyClient.getUsers().stream().forEach(user -> {
            Example<Membership> teamUserRoleExample = Example
                    .of(Membership
                            .builder()
                            .userId(user.getId())
                            .build());
            List<Membership> userTeams = membershipRepository.findAll(teamUserRoleExample);

            if(userTeams.isEmpty()){
                membershipRepository.save(Membership
                        .builder()
                        .userId(user.getId())
                        .build());
            }
        });
    }
}
