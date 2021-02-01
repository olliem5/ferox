package me.olliem5.ferox.api.friend;

import me.olliem5.ferox.api.module.ModuleManager;

import java.util.ArrayList;

public class FriendManager {
    private static ArrayList<Friend> friends = new ArrayList<>();

    public static ArrayList<Friend> getFriends() {
        return friends;
    }

    public boolean isFriend(String name) {
        boolean isFriend = false;

        for (Friend friend : getFriends()) {
            if (friend.getName().equalsIgnoreCase(name)) {
                isFriend = true;
            }
        }

        return isFriend;
    }

    public Friend getFriendByName(String name) {
        Friend namedFriend = null;

        for (Friend friend : getFriends()) {
            if (friend.getName().equalsIgnoreCase(name)) {
                namedFriend = friend;
            }
        }

        return namedFriend;
    }

    public void addFriend(String name) {
        friends.add(new Friend(name));
    }

    public void delFriend(String name) {
        friends.remove(getFriendByName(name));
    }

    public boolean isFriendsActive() {
        return ModuleManager.getModuleByName("Friends").isEnabled();
    }
}
