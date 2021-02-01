package me.olliem5.ferox.api.user;

import java.util.ArrayList;

public class FeroxUser {
    private String discord;
    private ArrayList<String> uuids;
    private int uid;

    public FeroxUser(String discord, ArrayList<String> uuids, int uid) {
        this.discord = discord;
        this.uuids = uuids;
        this.uid = uid;
    }

    public String getDiscord() {
        return discord;
    }

    public int getUid() {
        return uid;
    }
}
