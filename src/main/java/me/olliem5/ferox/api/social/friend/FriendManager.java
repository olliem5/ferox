package me.olliem5.ferox.api.social.friend;

import me.olliem5.ferox.api.module.ModuleManager;
import me.olliem5.ferox.api.social.friend.Friend;
import me.olliem5.ferox.impl.modules.ferox.Social;

import java.util.ArrayList;

/**
 * @author olliem5
 */

public final class FriendManager {
    private static ArrayList<Friend> friends = new ArrayList<>();

    public static boolean isFriend(String name) {
        boolean isFriend = false;

        for (Friend friend : getFriends()) {
            if (friend.getName().equalsIgnoreCase(name) && ModuleManager.getModuleByName("Social").isEnabled() && Social.friends.getValue()) {
                isFriend = true;
            }
        }

        return isFriend;
    }

    public static Friend getFriendByName(String name) {
        Friend namedFriend = null;

        for (Friend friend : getFriends()) {
            if (friend.getName().equalsIgnoreCase(name)) {
                namedFriend = friend;
            }
        }

        return namedFriend;
    }

    public static ArrayList<Friend> getFriends() {
        return friends;
    }

    public static void addFriend(String name) {
        friends.add(new Friend(name));
    }

    public static void delFriend(String name) {
        friends.remove(getFriendByName(name));
    }
}
