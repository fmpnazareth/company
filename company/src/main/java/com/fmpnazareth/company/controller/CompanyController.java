package com.fmpnazareth.company.controller;

import com.fmpnazareth.company.domain.Role;
import com.fmpnazareth.company.domain.User;
import com.fmpnazareth.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(this.companyService.getUsers());
    }

    @PutMapping("/role")
    public ResponseEntity<User> createRole(@RequestBody String roleName) {
        companyService.createRole(roleName);
        return ResponseEntity.ok().build();
    }
}
