package com.avihai789.core.githubsearch.repository;

import com.avihai789.core.githubsearch.model.tables.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepo extends JpaRepository<SearchHistory, Integer> {

    SearchHistory findByUserName(String userName);

    List<SearchHistory> findByOrderBySearchCountDesc();

}
