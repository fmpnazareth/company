package com.fmpnazareth.company.domain.controller;

import com.fmpnazareth.company.dto.User;
import com.fmpnazareth.company.domain.Role;
import com.fmpnazareth.company.domain.Membership;
import com.fmpnazareth.company.domain.service.CompanyService;
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

    @GetMapping(value = "/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(this.companyService.getRoles());
    }

    @GetMapping(value = "/memberships")
    public ResponseEntity<List<Membership>> getMemberships() {
        return ResponseEntity.ok(this.companyService.getMemberships());
    }

    @PutMapping(value = "/role/{roleName}")
    public ResponseEntity<User> createRole(@PathVariable(value = "roleName", required = true) String roleName) {
        companyService.createRole(roleName);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/role/assign/{roleId}")
    public ResponseEntity<Void> assignRole(@PathVariable(value = "roleId", required = true) Integer roleId,
                                           @RequestParam(value = "userId", required = true) String userId,
                                           @RequestParam(value = "teamId", required = true) String teamId) {
        companyService.assignRole(roleId, userId, teamId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/role")
    public ResponseEntity<Role> lookUpRoleForMembership(@RequestParam(value = "userId", required = true) String userId,
                                                        @RequestParam(value = "teamId", required = true) String teamId) {
        Role roleName = companyService.lookUpRoleForMembership(userId, teamId);

        if(roleName == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(roleName);
    }

    @GetMapping(value = "/memberships/{roleId}")
    public ResponseEntity<List<Membership>> lookUpMembershipForRole(@PathVariable(value = "roleId", required = true) Integer roleId) {
        List<Membership> membershipResourceList = companyService.lookUpMembershipForRole(roleId);

        if(membershipResourceList == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(membershipResourceList);
    }
}
