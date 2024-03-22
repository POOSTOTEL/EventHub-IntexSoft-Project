package com.Eventhub.EventHubIntexSoft.payload.response;

import lombok.Data;

@Data
public class GitHubAuthorizeResponse {
    private String access_token;
    private String token_type;
}
