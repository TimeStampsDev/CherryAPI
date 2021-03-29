package net.cherryflavor.api.chat.tools;

import net.cherryflavor.api.mojang.GameAchievements;
import net.cherryflavor.api.tools.TextFormat;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.security.InvalidParameterException;

/**
 *
 * Created on 2/20/2021
 * Time 12:32 AM
 *
 * DOES NOT SUPPORT {@link HoverEvent.Action SHOW_ITEM} THIS IS BECAUSE ITEMSTACK IS IN SERVER SIDE, NOT PROXY!
 */
@Deprecated
public class HoverComponent {

    public String hoverText;
    public HoverEvent.Action action;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public HoverComponent(String hoverText) {
        this.hoverText = hoverText;
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Creates hover event
     *
     * Object can only be a String, String..., or GameAchievements
     * @param action
     * @param object
     * @return
     */
     public static HoverEvent addHoverEvent(HoverEvent.Action action, Object... object) {
        if (object[0] instanceof String || object instanceof String[]) {
            switch (object.length) {
                case 1:
                    String string = (String) object[0];
                    ComponentBuilder component = new ComponentBuilder(string);
                    return new HoverEvent(action, component.create());
                case 0:
                    throw new NullPointerException("Object array can not be empty");
                default:
                    ComponentBuilder builder = new ComponentBuilder();
                    for (String s : (String[]) object) {
                        builder.append(s);
                    }
                    return new HoverEvent(action, builder.create());
            }
        } else if (object[0] instanceof GameAchievements) {
            switch (object.length) {
                case 1:
                    GameAchievements achievement = (GameAchievements) object[0];
                    return new HoverEvent(action, TextComponent.fromLegacyText(TextFormat.colorize("&e" + achievement.getAchievementLabel()+ "&f") + "\n" + achievement.getDescription()));
                default:
                    throw new InvalidParameterException("Cannot have more than one GameAchievements listed");
            }
        } else {
            throw new InvalidParameterException("Object can only be String, String..., or GameAchievements");
        }
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * Shows show
     * @param show
     * @return
     */
    public TextComponent showText(String show) {
        this.action = HoverEvent.Action.SHOW_TEXT;
        TextComponent newHoverEvent = new TextComponent(hoverText);
        newHoverEvent.setHoverEvent(new HoverEvent(action, TextComponent.fromLegacyText(show)));
        return newHoverEvent;
    }

    /**
     * Shows show
     * @param show
     * @return
     */
    public TextComponent showText(String... show) {
        this.action = HoverEvent.Action.SHOW_TEXT;
        TextComponent newHoverEvent = new TextComponent(hoverText);
        ComponentBuilder builder = new ComponentBuilder();

        for (String t : show) {
            builder.append(t + "\n");
        }

        newHoverEvent.setHoverEvent(new HoverEvent(action, builder.create()));
        return newHoverEvent;
    }

    /**
     * Shows text
     * @param show
     * @return
     */
    public BaseComponent componentShowText(String show) {
        this.action = HoverEvent.Action.SHOW_TEXT;
        ComponentBuilder builder = new ComponentBuilder();
        builder.append(show);
        builder.event(new HoverEvent(action, TextComponent.fromLegacyText(show)));
        return builder.create()[0];
    }

    /**
     * Shows text
     * @param show
     * @return
     */
    public BaseComponent componentsShowText(String... show) {
        this.action = HoverEvent.Action.SHOW_TEXT;
        ComponentBuilder builder = new ComponentBuilder(hoverText);
        ComponentBuilder showBuilder = new ComponentBuilder();
        for (String t : show) {
            showBuilder.append(t);
        }
        builder.event(new HoverEvent(action, showBuilder.create()));
        return builder.create()[0];
    }

    /**
     * Shows achievement description
     * @param achievement
     * @return
     */
    public TextComponent showAchievement(GameAchievements achievement) {
        this.action = HoverEvent.Action.SHOW_TEXT;
        TextComponent newHoverEvent = new TextComponent(hoverText);
        newHoverEvent.setHoverEvent(new HoverEvent(action, TextComponent.fromLegacyText(TextFormat.colorize("&e" + achievement.getAchievementLabel()+ "&f") + "\n" + achievement.getDescription())));
        return newHoverEvent;
    }

}
