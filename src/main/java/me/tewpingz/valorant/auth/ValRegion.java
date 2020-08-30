package me.tewpingz.valorant.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  ValRegion {

    NA("https://pd.na.a.pvp.net"),
    EU("https://pd.eu.a.pvp.net"),
    AP("https://pd.ap.a.pvp.net");

    /* The endpoint for the region */
    private final String url;

}
