package indrocraftapi;

import org.bukkit.Bukkit;

@SuppressWarnings("unused")
public final class Utils {
    public void sayHi() {
        Bukkit.getLogger().severe("Hello I am API!!");
    }

    public static void sayHiStatic() {
        Bukkit.getLogger().severe("Hello I am a static method in an API!!");
    }
}
