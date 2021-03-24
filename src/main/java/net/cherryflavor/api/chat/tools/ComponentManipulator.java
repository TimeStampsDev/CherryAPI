package net.cherryflavor.api.chat.tools;

import net.cherryflavor.api.mojang.GameAchievements;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class ComponentManipulator {

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Method #1
     * Adds ClickEvent.Action.OPEN_URL to provided text component
     * @param component
     * @param url
     * @return
     */
    public static TextComponent addOpenURL(TextComponent component, String url) {
        component.setClickEvent(ClickComponent.addClickEvent(ClickEvent.Action.OPEN_URL, url));
        return component;
    }

    /**
     * Method #1
     * Adds HoverEvent.Action.SHOW_TEXT to provided text component
     * @param component
     * @param text
     * @return
     */
    @Deprecated
    public static TextComponent addHoverText(TextComponent component, String... text) {
        component.setHoverEvent( HoverComponent.addHoverEvent(HoverEvent.Action.SHOW_TEXT, text));
        return component;
    }

    /**
     * Method #2
     * Adds HoverEvent.Action.SHOW_ACHIEVEMENT to provided text component
     * @param component
     * @param achievement
     * @return
     */
    public static TextComponent addHoverText(TextComponent component, GameAchievements achievement) {
        component.setHoverEvent(HoverComponent.addHoverEvent(HoverEvent.Action.SHOW_TEXT, achievement));
        return component;
    }
}
