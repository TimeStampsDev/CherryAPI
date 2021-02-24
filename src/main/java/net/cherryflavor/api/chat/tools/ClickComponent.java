package net.cherryflavor.api.chat.tools;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class ClickComponent {

    public String clickText;
    public ClickEvent.Action action;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     *
     * @param clickText
     */
    public ClickComponent(String clickText) {
        this.clickText = clickText;
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Creates a ClickEvent
     * @param action
     * @param input
     * @return
     */
    public static ClickEvent addClickEvent(ClickEvent.Action action, String input) {
        return new ClickEvent(action, input);
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * When text is clicked, opens url
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
     * When text is clicked, opens file
     * @param filePath
     * @return
     */
    public TextComponent openFile(String filePath) {
        action = ClickEvent.Action.OPEN_FILE;
        TextComponent newClickComponent = new TextComponent(clickText);
        newClickComponent.setClickEvent(new ClickEvent(action, filePath));
        return newClickComponent;
    }

    /**
     * When text is clicked, opens file
     * @param filePath
     * @return
     */
    public BaseComponent componetOpenFile(String filePath) {
        this.action = ClickEvent.Action.OPEN_URL;
        ComponentBuilder builder = new ComponentBuilder();
        builder.append(clickText);
        builder.event(new ClickEvent(action, filePath));
        return builder.create()[0];
    }

    /**
     * When text is clicked, runs command
     * @param command
     * @return
     */
    public TextComponent runCommand(String command) {
        this.action = ClickEvent.Action.RUN_COMMAND;
        TextComponent newClickComponent = new TextComponent(clickText);
        newClickComponent.setClickEvent(new ClickEvent(action, command));
        return newClickComponent;
    }

    /**
     * When text is clicked, runs command
     * @param command
     * @return
     */
    public BaseComponent componentRunCommand(String command) {
        this.action = ClickEvent.Action.RUN_COMMAND;
        ComponentBuilder builder = new ComponentBuilder();
        builder.append(clickText);
        builder.event(new ClickEvent(action, command));
        return builder.create()[0];
    }

    /**
     * When text is clicked, suggests command
     * @param command
     * @return
     */
    public TextComponent suggestCommand(String command) {
        this.action = ClickEvent.Action.SUGGEST_COMMAND;
        TextComponent newClickComponent = new TextComponent(clickText);
        newClickComponent.setClickEvent(new ClickEvent(action, command));
        return newClickComponent;
    }

    /**
     * When text is clicked, suggests command
     * @param command
     * @return
     */
    public BaseComponent componentSuggestCommand(String command) {
        this.action = ClickEvent.Action.SUGGEST_COMMAND;
        ComponentBuilder builder = new ComponentBuilder();
        builder.append(clickText);
        builder.event(new ClickEvent(action, command));
        return builder.create()[0];
    }

    /**
     * When text is clicked, copies text to clipboard
     * @param text
     * @return
     */
    public TextComponent copyText(String text) {
        this.action = ClickEvent.Action.COPY_TO_CLIPBOARD;
        TextComponent newClickComponent = new TextComponent(clickText);
        newClickComponent.setClickEvent(new ClickEvent(action, text));
        return newClickComponent;
    }

    /**
     * When text is clicked, copies text to clipboard
     * @param text
     * @return
     */
    public BaseComponent componentCopyText(String text) {
        this.action = ClickEvent.Action.COPY_TO_CLIPBOARD;
        ComponentBuilder builder = new ComponentBuilder();
        builder.append(clickText);
        builder.event(new ClickEvent(action, text));
        return builder.create()[0];
    }

    /**
     * When text is clicked, changes page
     * @param nextPageText
     * @return
     */
    public TextComponent changePage(String nextPageText) {
        this.action = ClickEvent.Action.CHANGE_PAGE;
        TextComponent newClickComponent = new TextComponent(clickText);
        newClickComponent.setClickEvent(new ClickEvent(action, nextPageText));
        return newClickComponent;
    }

    /**
     * When text is clicked, changes page
     * @param nextPageText
     * @return
     */
    public BaseComponent componentPagePage(String nextPageText) {
        this.action = ClickEvent.Action.CHANGE_PAGE;
        ComponentBuilder builder = new ComponentBuilder();
        builder.append(clickText);
        builder.event(new ClickEvent(action, nextPageText));
        return builder.create()[0];
    }

}
