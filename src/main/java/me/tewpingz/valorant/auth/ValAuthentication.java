package me.tewpingz.valorant.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValAuthentication {

    private final String uniqueId;
    private final String token;
    private final String entertainmentToken;

}
