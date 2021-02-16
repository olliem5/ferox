package me.olliem5.ferox.api.social.enemy;

import me.olliem5.ferox.api.module.ModuleManager;
import me.olliem5.ferox.impl.modules.ferox.Social;

import java.util.ArrayList;

/**
 * @author olliem5
 */

public final class EnemyManager {
    private static ArrayList<Enemy> enemies = new ArrayList<>();

    public static boolean isEnemy(String name) {
        boolean isEnemy = false;

        for (Enemy enemy : getEnemies()) {
            if (enemy.getName().equalsIgnoreCase(name) && ModuleManager.getModuleByName("Social").isEnabled() && Social.enemies.getValue()) {
                isEnemy = true;
            }
        }

        return isEnemy;
    }

    public static Enemy getEnemyByName(String name) {
        Enemy namedEnemy = null;

        for (Enemy enemy : getEnemies()) {
            if (enemy.getName().equalsIgnoreCase(name)) {
                namedEnemy = enemy;
            }
        }

        return namedEnemy;
    }

    public static ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public static void addEnemy(String name) {
        enemies.add(new Enemy(name));
    }

    public static void delEnemy(String name) {
        enemies.remove(getEnemyByName(name));
    }
}
