package com.fmpnazareth.company.api.controller;

import com.fmpnazareth.company.api.resource.RoleResource;
import com.fmpnazareth.company.domain.User;
import com.fmpnazareth.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(this.companyService.getUsers());
    }

    @PutMapping(value = "/role")
    public ResponseEntity<User> createRole(@RequestBody RoleResource roleResource) {
        companyService.createRole(roleResource);
        return ResponseEntity.ok().build();
    }
}
