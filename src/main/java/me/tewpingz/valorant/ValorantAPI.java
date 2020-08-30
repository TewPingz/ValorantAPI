package me.tewpingz.valorant;

import com.google.gson.JsonObject;
import me.tewpingz.valorant.auth.ValAuthentication;
import me.tewpingz.valorant.auth.ValHeader;
import me.tewpingz.valorant.auth.ValRegion;
import org.apache.http.HttpHeaders;

import java.io.IOException;

public class ValorantAPI {

    private static final Valorant valorantInstance = new Valorant();
    private static final String latestVersion = "release-01.05-shipping-15-460559";

    public static ValAuthentication login(String username, String password) throws IOException {
        valorantInstance.resetExecutor();
        return valorantInstance.auth(username, password);
    }

    public static JsonObject getStore(ValAuthentication valAuthentication, ValRegion valRegion) throws IOException {
        ValHeader authHeader = new ValHeader(HttpHeaders.AUTHORIZATION, "Bearer " + valAuthentication.getToken());
        ValHeader enHeader = new ValHeader("X-Riot-Entitlements-JWT", valAuthentication.getEntertainmentToken());
        return valorantInstance.get(valRegion.getUrl() + "/store/v2/storefront/" + valAuthentication.getUniqueId(), authHeader, enHeader).getAsJsonObject();
    }

    public static JsonObject getIds(ValAuthentication valAuthentication, ValRegion valRegion) throws IOException {
        return getIds(valAuthentication, valRegion, latestVersion);
    }

    public static JsonObject getIds(ValAuthentication valAuthentication, ValRegion valRegion, String version) throws IOException {
        ValHeader authHeader = new ValHeader(HttpHeaders.AUTHORIZATION, "Bearer " + valAuthentication.getToken());
        ValHeader enHeader = new ValHeader("X-Riot-Entitlements-JWT", valAuthentication.getEntertainmentToken());
        ValHeader verHeader = new ValHeader("X-Riot-ClientVersion", version);
        return valorantInstance.get(valRegion.getUrl().replace("pd", "shared") + "/content-service/v2/content", authHeader, enHeader, verHeader).getAsJsonObject();
    }

    public static JsonObject getBalance(ValAuthentication valAuthentication, ValRegion valRegion) throws IOException {
        ValHeader authHeader = new ValHeader(HttpHeaders.AUTHORIZATION, "Bearer " + valAuthentication.getToken());
        ValHeader enHeader = new ValHeader("X-Riot-Entitlements-JWT", valAuthentication.getEntertainmentToken());
        return valorantInstance.get(valRegion.getUrl() + "/store/v1/wallet/" + valAuthentication.getUniqueId(), authHeader, enHeader).getAsJsonObject();
    }

    public static JsonObject getCompetitiveHistory(ValAuthentication valAuthentication, ValRegion valRegion) throws IOException {
        ValHeader authHeader = new ValHeader(HttpHeaders.AUTHORIZATION, "Bearer " + valAuthentication.getToken());
        ValHeader enHeader = new ValHeader("X-Riot-Entitlements-JWT", valAuthentication.getEntertainmentToken());
        return valorantInstance.get(valRegion.getUrl() + "/mmr/v1/players/" + valAuthentication.getUniqueId() + "/competitiveupdates",
                authHeader, enHeader).getAsJsonObject();
    }
}
