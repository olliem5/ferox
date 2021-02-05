package me.olliem5.ferox.api.user;

import java.util.ArrayList;

public final class UserManager {
    private static final ArrayList<FeroxUser> feroxUsers = new ArrayList<>();

    /**
     * Developers
     */

    private static final ArrayList<String> ollieAccounts = new ArrayList<>();
    private static final ArrayList<String> drailyAccounts = new ArrayList<>();
    private static final ArrayList<String> reapAccounts = new ArrayList<>();
    private static final ArrayList<String> linusAccounts = new ArrayList<>();

    /**
     * Users
     */

    private static final ArrayList<String> nekoAccounts = new ArrayList<>();
    private static final ArrayList<String> occultAccounts = new ArrayList<>();
    private static final ArrayList<String> fourteenAccounts = new ArrayList<>();
    private static final ArrayList<String> masonAccounts = new ArrayList<>();
    private static final ArrayList<String> kaeyAccounts = new ArrayList<>();
    private static final ArrayList<String> tbmAccounts = new ArrayList<>();
    private static final ArrayList<String> tripiAccounts = new ArrayList<>();
    private static final ArrayList<String> glAccounts = new ArrayList<>();
    private static final ArrayList<String> miloAccounts = new ArrayList<>();
    private static final ArrayList<String> firerugAccounts = new ArrayList<>();
    private static final ArrayList<String> quillAccounts = new ArrayList<>();
    private static final ArrayList<String> perryAccounts = new ArrayList<>();
    private static final ArrayList<String> runeAccounts = new ArrayList<>();
    private static final ArrayList<String> maneskoAccounts = new ArrayList<>();

    public UserManager() {
        feroxUsers.add(new FeroxUser("ollie#0057", ollieAccounts, 1));
        ollieAccounts.add("8deac414-6c37-44fb-82bd-6873efc1b0cf"); //_o_b_a_m_a_
        ollieAccounts.add("b92ec9a1-29e1-4916-b2a5-14d15a167220"); //FeroxClient
        ollieAccounts.add("7720689b-da0e-406e-8183-459dcdbf1d6c"); //Spawn_Goon
    }
}
