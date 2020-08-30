package me.tewpingz.valorant.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValHeader {

    /* The key of the valorant header */
    private final String key;

    /* The value of the valorant header */
    private final String value;

}
