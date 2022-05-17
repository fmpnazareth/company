package com.fmpnazareth.company.domain.service;

import com.fmpnazareth.company.dto.User;
import com.fmpnazareth.company.external.ExternalCompanyClient;
import com.fmpnazareth.company.domain.repository.RoleRepository;
import com.fmpnazareth.company.domain.repository.MembershipRepository;
import com.fmpnazareth.company.domain.Role;
import com.fmpnazareth.company.domain.Membership;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompanyService {

    @Autowired
    ExternalCompanyClient externalCompanyClient;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MembershipRepository membershipRepository;

    public List<User> getUsers() {
        return externalCompanyClient.getUsers();
    }

    public Optional<User> getUser(String id) {
        return externalCompanyClient.getUser(id);
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public List<Membership> getMemberships() {
        return membershipRepository.findAll();
    }

    @SneakyThrows
    public void createRole(String roleName) {

        Role newRole = Role.builder()
                .name(roleName)
                .build();

        Example<Role> roleExample = Example.of(newRole);

        if (!roleRepository.findAll(roleExample).isEmpty()) {
            String errorMsg = String.format("Role %s already exists", roleName);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }

        roleRepository.save(newRole);
    }

    @SneakyThrows
    public void assignRole(Integer roleId, String userId, String teamId) {

        if (roleRepository.findById(roleId).isEmpty()) {
            String errorMsg = String.format("Role %s not exists", roleId);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }

        Membership membership = Membership.builder()
                .userId(userId)
                .teamId(teamId)
                .build();

        Example<Membership> teamUserRoleExample = Example.of(membership);
        List<Membership> allUsersOfTeam = membershipRepository.findAll(teamUserRoleExample);

        if (allUsersOfTeam.isEmpty()) {
            String errorMsg = String.format("Not exists membership with userId:%s and teamId:%s", userId, teamId);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }

        allUsersOfTeam.stream().forEach(userOfTeam -> {
            userOfTeam.setRoleId(roleId);
            membershipRepository.save(userOfTeam);
        });
    }

    @SneakyThrows
    public Role lookUpRoleForMembership(String userId, String teamId) {

        Membership membership = Membership.builder()
                .userId(userId)
                .teamId(teamId)
                .build();

        Example<Membership> teamUserRoleExample = Example.of(membership);

        List<Membership> allUsersOfTeam = membershipRepository.findAll(teamUserRoleExample)
                .stream().filter(Objects::nonNull).filter(t -> t.getRoleId() != null).collect(Collectors.toList());

        if (allUsersOfTeam.isEmpty()) {
            return null;
        }

        Optional<Role> role = roleRepository.findById(allUsersOfTeam.get(0).getRoleId());

        if(role.isEmpty()){
            return null;
        }

        return role.get();

    }

    public List<Membership> lookUpMembershipForRole(Integer roleId) {
        Role newRole = Role.builder()
                .id(roleId)
                .build();

        Example<Role> roleExample = Example.of(newRole);

        List<Role> roles = roleRepository.findAll(roleExample);

        if (roles.isEmpty()) {
            return null;
        }

        Membership membership = Membership.builder()
                .roleId(roleId)
                .build();

        Example<Membership> teamUserRoleExample = Example.of(membership);

        List<Membership> membershipList = membershipRepository.findAll(teamUserRoleExample);

        if (membershipList.isEmpty()) {
            return null;
        }

        return membershipList;
    }

}
