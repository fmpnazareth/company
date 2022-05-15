package com.fmpnazareth.company.service;

import com.fmpnazareth.company.domain.Role;
import com.fmpnazareth.company.domain.User;
import com.fmpnazareth.company.external.TeamExternalClient;
import com.fmpnazareth.company.repository.RoleRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompanyService {

    @Autowired
    TeamExternalClient teamExternalClient;

    @Autowired
    RoleRepository roleRepository;

    public List<User> getUsers(){
        return teamExternalClient.getUsers();
    }

    public Optional<User> getUser(String id){
        return teamExternalClient.getUser(id);
    }

    @SneakyThrows
    public void createRole(String roleName) {
        Role newRole = Role.builder()
                .name(roleName)
                .build();

        Example<Role> roleExample = Example.of(newRole);

        if(!roleRepository.findAll(roleExample).isEmpty()){
            String errorMsg = String.format("Role %s already exists", roleName);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }

        roleRepository.save(newRole);
    }
}
