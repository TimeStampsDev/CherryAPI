package net.cherryflavor.api.other.help;

import net.cherryflavor.api.bungee.ProxyAPI;
import net.cherryflavor.api.configuration.Configuration;
import net.cherryflavor.api.exceptions.InvalidAPIObjectException;
import net.cherryflavor.api.other.PageMaker;
import net.cherryflavor.api.other.PagePreviewBuilder;
import net.cherryflavor.api.spigot.ServerAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/7/2021
 * Time 3:26 PM
 */

public class HelpPageMaker extends PageMaker {

    private Object api;

    public HelpPageMaker(Object api) {
        super(new ArrayList<>(), 10);
        if (api instanceof ServerAPI) {
            this.api = api;
        } else if (api instanceof ProxyAPI) {
            this.api = api;
        } else {
            throw new InvalidAPIObjectException("Invalid api type");
        }
    }

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public void addCommandHelp(String subCommand, String description) {
        String cmdColor = getBasicMessages().getString("help.command-color");
        String descColor = getBasicMessages().getString("help.description-color");
        addData(cmdColor + subCommand);
        addData("__" + descColor + description.replace("\n", "\n__"));
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    private Configuration getBasicMessages() {
        if (api instanceof ServerAPI) {
            return ((ServerAPI) api).getBasicMessages();
        } else if (api instanceof ProxyAPI) {
            return ((ProxyAPI) api).getBasicMessages();
        }
        return null;
    }

    public List<String> getPagePreview(int page) {
        PagePreviewBuilder previewBuilder = new PagePreviewBuilder(this);
        for (String h : getBasicMessages().getString("help.header").split("\n")) {
            previewBuilder.addHeader(h);
        }
        for (String f : getBasicMessages().getString("help.footer").split("\n")) {
            previewBuilder.addFooter(f);
        }
        return previewBuilder.build(page, false);
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================


}
