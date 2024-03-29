package com.avihai789.core.githubsearch.service;

import com.avihai789.core.githubsearch.model.dto.Search;
import com.avihai789.core.githubsearch.model.tables.SearchHistory;
import com.avihai789.core.githubsearch.repository.SearchHistoryRepo;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class SearchService {

    @Value("${github.base.api.url}")
    private String url;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    private SearchHistoryRepo historyRepo;

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
            SearchHistory searchHistory = historyRepo.findByUserName(userName);
            SearchHistory history = new SearchHistory();
            if(searchHistory==null){
                history.setUserName(userName);
                history.setFullName(results.getName());
                history.setGitUrl(results.getHtml_url());
                history.setSearchCount(1);
                history.setCreatedAt(LocalDateTime.now());
            } else {
                history.setId(searchHistory.getId());
                history.setUserName(searchHistory.getUserName());
                history.setFullName(searchHistory.getFullName());
                history.setGitUrl(searchHistory.getGitUrl());
                history.setSearchCount(searchHistory.getSearchCount()+1);
                history.setUpdatedAt(LocalDateTime.now());
            }
            historyRepo.save(history);
            return results;
        } catch (HttpStatusCodeException exception) {
            int statusCode = exception.getStatusCode().value();
            log.info("error code: {}", statusCode);
        }
        return results;
    }

    public List<SearchHistory> findPopularSearchHistory() {
        List<SearchHistory> histories = new ArrayList<>();
        AtomicLong count= new AtomicLong();
        historyRepo.findByOrderBySearchCountDesc().stream().forEach(searchHistory -> {
            if(count.getAndIncrement()<10){
                histories.add(searchHistory);
            }
        });
        return histories;
    }

    public void deleteAll() {
        historyRepo.deleteAll();
    }
}
