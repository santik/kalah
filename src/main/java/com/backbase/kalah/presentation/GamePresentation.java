package com.backbase.kalah.presentation;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class GamePresentation {
    private String id;
    private String url;
    private Map<String, String> status;
}
