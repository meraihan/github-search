package com.avihai789.core.githubsearch.model.dto;

import lombok.Data;


@Data
public class SearchResult {
    public Search Search;
    public String totalResults;
    public String Response;
}
