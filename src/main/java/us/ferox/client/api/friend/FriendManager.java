package us.ferox.client.api.friend;

import us.ferox.client.Ferox;

import java.util.ArrayList;

public class FriendManager {
    private ArrayList<Friend> friends = new ArrayList<>();

    public ArrayList<Friend> getFriends() {
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
        Friend fr = null;

        for (Friend friend : getFriends()) {
            if (friend.getName().equalsIgnoreCase(name)) {
                fr = friend;
            }
        }

        return fr;
    }

    public void addFriend(String name) {
        friends.add(new Friend(name));
    }

    public void delFriend(String name) {
        friends.remove(getFriendByName(name));
    }

    public boolean isFriendsActive() {
        return Ferox.moduleManager.getModuleByName("Friends").isEnabled();
    }
}
