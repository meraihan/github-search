package com.avihai789.core.githubsearch.repository;

import com.avihai789.core.githubsearch.model.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SearchHistoryRepo extends JpaRepository<SearchHistory, Integer> {
    SearchHistory findByUserName(String userName);
}
