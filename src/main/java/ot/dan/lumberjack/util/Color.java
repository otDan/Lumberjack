package ot.dan.lumberjack.util;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Color {
    public static String translate(String string) {
        return HexColor.colorify(ChatColor.translateAlternateColorCodes('&', string));
    }

    public static List<String> translate(List<String> list) {
        List<String> newList = new ArrayList<>();
        for (String string : list) {
            newList.add(HexColor.colorify(ChatColor.translateAlternateColorCodes('&', string)));
        }
        return newList;
    }
}

