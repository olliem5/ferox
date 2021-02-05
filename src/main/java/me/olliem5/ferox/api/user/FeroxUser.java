package me.olliem5.ferox.api.user;

import java.util.ArrayList;

public final class FeroxUser {
    private final String discord;
    private final ArrayList<String> uuids;
    private final int uid;

    public FeroxUser(String discord, ArrayList<String> uuids, int uid) {
        this.discord = discord;
        this.uuids = uuids;
        this.uid = uid;
    }

    public String getDiscord() {
        return discord;
    }

    public ArrayList getUuids() {
        return uuids;
    }

    public int getUid() {
        return uid;
    }
}
