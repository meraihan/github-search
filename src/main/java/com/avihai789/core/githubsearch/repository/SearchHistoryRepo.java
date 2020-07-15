package com.avihai789.core.githubsearch.repository;

import com.avihai789.core.githubsearch.model.SearchHistory;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepo extends JpaRepository<SearchHistory, Integer> {
    SearchHistory findByUserName(String userName);

    @Query(value = "SELECT * FROM search_history ORDER BY " +
            "search_count DESC LIMIT 10")
    List<SearchHistory> findByOrderBySearchCountDesc();


}
