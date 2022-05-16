package com.fmpnazareth.company.external;

import com.fmpnazareth.company.domain.Team;
import com.fmpnazareth.company.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "externalCompany",
        url = "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com")
public interface ExternalCompanyClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    List<User> getUsers();

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    Optional<User> getUser(@PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.GET, value = "/teams")
    List<Team> getTeams();

    @RequestMapping(method = RequestMethod.GET, value = "/teams/{id}")
    Optional<Team> getTeam(@PathVariable("id") String id);
}
