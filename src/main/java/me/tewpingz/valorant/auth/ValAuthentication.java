package me.tewpingz.valorant.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValAuthentication {

    /* The uniqueId of the account */
    private final String uniqueId;

    /* The verification token for the account. This expire after 10-15 minutes of authentication */
    private final String token;

    /* The entertainment token. This expire after 10-15 minutes of authentication */
    private final String entertainmentToken;

}
