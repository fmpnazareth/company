package com.fmpnazareth.company.unit.controller;

import com.fmpnazareth.company.domain.service.CompanyService;
import com.fmpnazareth.company.external.ExternalCompanyClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class CompanyControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	CompanyService companyService;

	@MockBean
	ExternalCompanyClient externalCompanyClient;


	@SneakyThrows
	@Test
	void testCreateRole(){
		mvc.perform(put("/v1/company/role")
						.param("roleName", "Developer2"))
				.andExpect(status().isOk());

		mvc.perform(put("/v1/company/role"))
				.andExpect(status().isBadRequest());
	}

	@SneakyThrows
	@Test
	void testAssignRole(){
		mvc.perform(put("/v1/company/role/assign")
						.param("roleId", "1")
						.param("userId", "user1")
						.param("teamId", "team1"))
				.andExpect(status().isOk());

		mvc.perform(put("/v1/company/role/assign")
						.param("roleId", "not_number")
						.param("userId", "user1")
						.param("teamId", "team1"))
				.andExpect(status().isBadRequest());
	}

	@SneakyThrows
	@Test
	void testeLookUpRoleForMembership(){
		mvc.perform(get("/v1/company/role")
						.param("userId", "user1")
						.param("teamId", "team1"))
				.andExpect(status().isNoContent());

		when(companyService.lookUpRoleForMembership(any(), anyString())).thenReturn("RoleName");

		mvc.perform(get("/v1/company/role")
						.param("userId", "user1")
						.param("teamId", "team1"))
				.andExpect(status().isOk());

		mvc.perform(get("/v1/company/role")
						.param("teamId", "team1"))
				.andExpect(status().isBadRequest());
	}

	@SneakyThrows
	@Test
	void testeLookUpMembershipForRole(){

		when(companyService.lookUpMembershipForRole(any())).thenReturn(null);

		mvc.perform(get("/v1/company/memberships/1"))
				.andExpect(status().isNoContent());

		when(companyService.lookUpMembershipForRole(any())).thenReturn(new ArrayList<>());

		mvc.perform(get("/v1/company/memberships/1"))
				.andExpect(status().isOk());

		mvc.perform(get("/v1/company/memberships/not_number"))
				.andExpect(status().isBadRequest());
	}
}
