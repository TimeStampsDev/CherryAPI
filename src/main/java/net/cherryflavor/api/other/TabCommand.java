package net.cherryflavor.api.other;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class TabCommand {

    private int argument;
    private String whenArgumentIs;
    private List<String> tabList;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     * @param argument
     * @param tabList
     */
    public TabCommand(int argument, String whenArgumentIs, List<String> tabList) {
        this.argument = argument;
        this.whenArgumentIs = whenArgumentIs;
        this.tabList = tabList;
    }

    /**
     * @param argument
     * @param tabList
     */
    public TabCommand(int argument, String whenArgumentIs, String... tabList) {
        this.argument = argument;
        this.whenArgumentIs = whenArgumentIs;
        this.tabList = Arrays.asList(tabList);
    }

    public int getArgument() { return this.argument; }
    public String getWhenArgumentIs() { return this.whenArgumentIs; }
    public List<String> getTabList() { return this.tabList; }



}
