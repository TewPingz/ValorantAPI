package me.tewpingz.valorant.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  ValRegion {

    NA("https://pd.na.a.pvp.net"),
    EU("https://pd.eu.a.pvp.net"),
    AP("https://pd.ap.a.pvp.net");

    private final String url;

}
