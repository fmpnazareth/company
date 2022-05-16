package com.fmpnazareth.company.service;

import com.fmpnazareth.company.api.resource.RoleResource;
import com.fmpnazareth.company.repository.domain.Role;
import com.fmpnazareth.company.dto.User;
import com.fmpnazareth.company.external.ExternalCompanyClient;
import com.fmpnazareth.company.repository.RoleRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompanyService {

    @Autowired
    ExternalCompanyClient externalCompanyClient;

    @Autowired
    RoleRepository roleRepository;

    public List<User> getUsers(){
        return externalCompanyClient.getUsers();
    }

    public Optional<User> getUser(String id){
        return externalCompanyClient.getUser(id);
    }

    @SneakyThrows
    public void createRole(RoleResource roleResource) {
        Role newRole = Role.builder()
                .name(roleResource.getName())
                .build();

        Example<Role> roleExample = Example.of(newRole);

        if(!roleRepository.findAll(roleExample).isEmpty()){
            String errorMsg = String.format("Role %s already exists", roleResource.getName());
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }

        roleRepository.save(newRole);
    }
}
