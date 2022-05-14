package com.fmpnazareth.company.service;

import com.fmpnazareth.company.domain.User;
import com.fmpnazareth.company.external.TeamExternalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    TeamExternalClient teamExternalClient;

    public List<User> getUsers(){
        return teamExternalClient.getUsers();
    }

    public Optional<User> getUser(String id){
        return teamExternalClient.getUser(id);
    }


}
