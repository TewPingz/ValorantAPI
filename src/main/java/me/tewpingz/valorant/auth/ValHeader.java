package me.tewpingz.valorant.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValHeader {

    private final String key;
    private final String value;

}
