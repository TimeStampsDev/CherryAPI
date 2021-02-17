package net.cherryflavor.api.bungee.chat;

import net.md_5.bungee.api.chat.*;

public class ClickComponent {

    public String clickText;
    public ClickEvent.Action action;

    public ClickComponent(String clickText) {
        this.clickText = clickText;
    }

    /**
     * Opens URL when clicked
     * @param url
     * @return
     */
    public TextComponent openURL(String url) {
        action = ClickEvent.Action.OPEN_URL;
        TextComponent newClickComponent = new TextComponent(clickText);
        newClickComponent.setClickEvent(new ClickEvent(action, url));
        return newClickComponent;
    }

    /**
     * Opens URL when clicked, shows text when hovered over
     * @param url
     * @param hoverText
     * @return
     */
    public TextComponent openURLandShowText(String url, String hoverText) {
        action = ClickEvent.Action.OPEN_URL;
        TextComponent newClickComponent = new HoverComponent(clickText).showText(hoverText);
        newClickComponent.setClickEvent(new ClickEvent(action, url));
        return newClickComponent;
    }

    /**
     * When text is clicked, opens url
     * @param url
     * @return
     */
    public BaseComponent componentOpenURL(String url) {
        this.action = ClickEvent.Action.OPEN_URL;
        ComponentBuilder builder = new ComponentBuilder();
        builder.append(clickText);
        builder.event(new ClickEvent(action, url));
        return builder.create()[0];
    }

    /**
     * Opens URL when clicked, shows text when hovered over
     * @param url
     * @return
     */
    public BaseComponent componentOpenURLAndShowText(String url, String hoverText) {
        this.action = ClickEvent.Action.OPEN_URL;
        ComponentBuilder builder = new ComponentBuilder();
        builder.append(new HoverComponent(clickText).showText(hoverText));
        builder.event(new ClickEvent(action, url));
        return builder.create()[0];
    }

    /**
     * Opens URL when clicked, shows text when hovered over
     * @param url
     * @return
     */
    public BaseComponent componentOpenURLAndShowText(String url, String... hoverText) {
        this.action = ClickEvent.Action.OPEN_URL;
        ComponentBuilder builder = new ComponentBuilder();
        builder.append(new HoverComponent(clickText).showText(hoverText));
        builder.event(new ClickEvent(action, url));
        return builder.create()[0];
    }

}
