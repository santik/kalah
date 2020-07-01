package com.backbase.kalah.presentation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewGamePresentation {
    private String id;
    private String uri;
}
