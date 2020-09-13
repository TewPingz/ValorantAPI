package me.tewpingz.valorant;

import com.google.gson.JsonObject;
import me.tewpingz.valorant.auth.ValAuthentication;
import me.tewpingz.valorant.auth.ValHeader;
import me.tewpingz.valorant.auth.ValRegion;
import org.apache.http.HttpHeaders;

import java.io.IOException;

public class ValorantAPI {

    private static final Valorant valorantInstance = new Valorant();

    /**
     * Authenticate yourself to access the api with your login and password
     *
     * @param username the username of the account.
     * @param password the password of the account.
     * @return an authentication object with uniqueId, token and entertainment token.
     * @throws IOException if the request gets invalidated or you get rate limited.
     */
    public static ValAuthentication login(String username, String password) throws IOException {
        valorantInstance.resetExecutor();
        return valorantInstance.auth(username, password);
    }

    /**
     * Fetch your store using your valorant authentication and your valorant region
     * Make sure the region is the same region as the account because it will give you incorrect data.
     *
     * @param valAuthentication the valorant authentication object you receive when executing ValorantAPI#login
     * @param valRegion the region the account is located in for the correct information.
     * @return a JsonObject containing all of the store data.
     * @throws IOException if the request gets invalidated or you get rate limited.
     */
    public static JsonObject getStore(ValAuthentication valAuthentication, ValRegion valRegion) throws IOException {
        ValHeader authHeader = new ValHeader(HttpHeaders.AUTHORIZATION, "Bearer " + valAuthentication.getToken());
        ValHeader enHeader = new ValHeader("X-Riot-Entitlements-JWT", valAuthentication.getEntertainmentToken());
        return valorantInstance.get(valRegion.getUrl() + "/store/v2/storefront/" + valAuthentication.getUniqueId(), authHeader, enHeader).getAsJsonObject();
    }
    
    /**
     * Fetches the item ids so you can convert the uniqueIds of items to names.
     * Region isn't important here so it can be any.
     *
     * @param valAuthentication the valorant authentication object you receive when executing ValorantAPI#login
     * @param valRegion the region the account is located in for the correct information.
     * @param version the version you want the items for.
     * @return a JsonObject containing all of the ids.
     * @throws IOException if the request gets invalidated or you get rate limited.
     */
    public static JsonObject getIds(ValAuthentication valAuthentication, ValRegion valRegion, String version) throws IOException {
        ValHeader authHeader = new ValHeader(HttpHeaders.AUTHORIZATION, "Bearer " + valAuthentication.getToken());
        ValHeader enHeader = new ValHeader("X-Riot-Entitlements-JWT", valAuthentication.getEntertainmentToken());
        ValHeader verHeader = new ValHeader("X-Riot-ClientVersion", version);
        return valorantInstance.get(valRegion.getUrl().replace("pd", "shared") + "/content-service/v2/content", authHeader, enHeader, verHeader).getAsJsonObject();
    }

    /**
     * Fetches your valorant balance.
     * Make sure the region is the same region as the account because it will give you incorrect data.
     *
     * @param valAuthentication the valorant authentication object you receive when executing ValorantAPI#login
     * @param valRegion the region the account is located in for the correct information.
     * @return a JsonObject with all the balance data.
     * @throws IOException if the request gets invalidated or you get rate limited.
     */
    public static JsonObject getBalance(ValAuthentication valAuthentication, ValRegion valRegion) throws IOException {
        ValHeader authHeader = new ValHeader(HttpHeaders.AUTHORIZATION, "Bearer " + valAuthentication.getToken());
        ValHeader enHeader = new ValHeader("X-Riot-Entitlements-JWT", valAuthentication.getEntertainmentToken());
        return valorantInstance.get(valRegion.getUrl() + "/store/v1/wallet/" + valAuthentication.getUniqueId(), authHeader, enHeader).getAsJsonObject();
    }

    /**
     * Fetches your past 10 valorant competitive matches.
     * Make sure the region is the same region as the account because it will give you incorrect data.
     *
     * @param valAuthentication the valorant authentication object you receive when executing ValorantAPI#login
     * @param valRegion the region the account is located in for the correct information.
     * @return a JsonObject competitive the past 10 competitive matches.
     * @throws IOException if the request gets invalidated or you get rate limited.
     */
    public static JsonObject getCompetitiveHistory(ValAuthentication valAuthentication, ValRegion valRegion) throws IOException {
        ValHeader authHeader = new ValHeader(HttpHeaders.AUTHORIZATION, "Bearer " + valAuthentication.getToken());
        ValHeader enHeader = new ValHeader("X-Riot-Entitlements-JWT", valAuthentication.getEntertainmentToken());
        return valorantInstance.get(valRegion.getUrl() + "/mmr/v1/players/" + valAuthentication.getUniqueId() + "/competitiveupdates",
                authHeader, enHeader).getAsJsonObject();
    }
}
