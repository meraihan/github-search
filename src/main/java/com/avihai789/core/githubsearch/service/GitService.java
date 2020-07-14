package com.avihai789.core.githubsearch.service;

import com.avihai789.core.githubsearch.model.Search;
import com.avihai789.core.githubsearch.model.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GitService {

    @Value("${github.base.api.url}")
    private String url;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public Search findByUserName(String userName) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("just_a_sample_header", "just_a_simple_value");
        HttpEntity entity = new HttpEntity(headers);
        String url = this.url + "users/" + userName.trim();
        Search results = new Search();
        try {
            ResponseEntity<Search> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Search.class);
            log.info("Status: " + responseEntity.getStatusCode());
            results = responseEntity.getBody();
            return results;
        } catch (HttpStatusCodeException exception) {
            int statusCode = exception.getStatusCode().value();
            log.info("error code: {}", statusCode);
        }
        return results;
    }

}
