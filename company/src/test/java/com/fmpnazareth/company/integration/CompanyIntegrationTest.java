package com.fmpnazareth.company.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmpnazareth.company.domain.Role;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
class CompanyIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	ExternalCompanyClient externalCompanyClient;


	@SneakyThrows
	@Test
	void testCreateRole(){
		mvc.perform(put("/v1/company/role/Developer 2"))
				.andExpect(status().isOk());

		mvc.perform(get("/v1/company/roles"))
				.andExpect(status().isOk())
				.andExpect(response -> {
					String json = response.getResponse().getContentAsString();
					Role[] role = new ObjectMapper().readValue(json, Role[].class);
					assertThat(role[0].getId()).isEqualTo(1);
					assertThat(role[0].getName()).isEqualTo("Developer 2");
				});
	}
}
