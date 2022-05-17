package com.fmpnazareth.company.unit.service;

import com.fmpnazareth.company.domain.Membership;
import com.fmpnazareth.company.domain.Role;
import com.fmpnazareth.company.domain.repository.MembershipRepository;
import com.fmpnazareth.company.domain.repository.RoleRepository;
import com.fmpnazareth.company.domain.service.CompanyService;
import com.fmpnazareth.company.external.ExternalCompanyClient;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class CompanyServiceTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	CompanyService companyService;

	@MockBean
	ExternalCompanyClient externalCompanyClient;

	@MockBean
	RoleRepository roleRepository;

	@MockBean
	MembershipRepository membershipRepository;

	@SneakyThrows
	@Test
	void testErrorCreateRole(){
		boolean hasError = false;
		String roleName = "Existing Role";
		when(roleRepository.findAll((Example<Role>) any())).thenReturn(Arrays.asList(Role.builder().build()));
		try{
			companyService.createRole(roleName);
		}catch (Exception ex){
			Assert.assertEquals(ex.getMessage(), String.format("Role %s already exists", roleName));
		}
	}

	@SneakyThrows
	@Test
	void testErrorAssignRoleWithoutRole(){
		boolean hasError = false;
		Integer roleId = 1;
		when(roleRepository.findById(roleId)).thenReturn(Optional.empty());
		try{
			companyService.assignRole(roleId, "user1", "team1");
		}catch (Exception ex){
			Assert.assertEquals(ex.getMessage(), String.format("Role %s not exists", roleId));
		}
	}

	@SneakyThrows
	@Test
	void testErrorAssignRoleInvalidMembership(){
		boolean hasError = false;
		Integer roleId = 1;
		String userId = "userId";
		String teamId = "teamId";
		when(roleRepository.findById(roleId)).thenReturn(Optional.of(Role.builder().build()));
		when(membershipRepository.findAll((Example<Membership>) any())).thenReturn(new ArrayList<>());

		try{
			companyService.assignRole(roleId, userId, teamId);
		}catch (Exception ex){
			Assert.assertEquals(ex.getMessage(), String.format("Not exists membership with userId:%s and teamId:%s", userId, teamId));
		}
	}
}
