package me.olliem5.ferox.api.util.client;

import me.olliem5.ferox.api.user.FeroxUser;

import java.util.ArrayList;

public class UserUtil {
    private ArrayList<FeroxUser> feroxUsers = new ArrayList<>();

    /**
     * Developers
     */

    private ArrayList<String> ollieAccounts = new ArrayList<>();
    private ArrayList<String> drailyAccounts = new ArrayList<>();
    private ArrayList<String> reapAccounts = new ArrayList<>();
    private ArrayList<String> linusAccounts = new ArrayList<>();

    /**
     * Users
     */

    private ArrayList<String> nekoAccounts = new ArrayList<>();
    private ArrayList<String> occultAccounts = new ArrayList<>();
    private ArrayList<String> fourteenAccounts = new ArrayList<>();
    private ArrayList<String> masonAccounts = new ArrayList<>();
    private ArrayList<String> kaeyAccounts = new ArrayList<>();
    private ArrayList<String> tbmAccounts = new ArrayList<>();
    private ArrayList<String> tripiAccounts = new ArrayList<>();
    private ArrayList<String> glAccounts = new ArrayList<>();
    private ArrayList<String> miloAccounts = new ArrayList<>();
    private ArrayList<String> firerugAccounts = new ArrayList<>();
    private ArrayList<String> quillAccounts = new ArrayList<>();
    private ArrayList<String> perryAccounts = new ArrayList<>();
    private ArrayList<String> runeAccounts = new ArrayList<>();
    private ArrayList<String> maneskoAccounts = new ArrayList<>();

    public UserUtil() {
        feroxUsers.add(new FeroxUser("ollie#0057", ollieAccounts, 1));
        ollieAccounts.add("8deac414-6c37-44fb-82bd-6873efc1b0cf"); //_o_b_a_m_a_
        ollieAccounts.add("b92ec9a1-29e1-4916-b2a5-14d15a167220"); //FeroxClient
        ollieAccounts.add("7720689b-da0e-406e-8183-459dcdbf1d6c"); //Spawn_Goon
    }
}
