package com.fmpnazareth.company.service;

import com.fmpnazareth.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository accountRepository;
}
