package net.cherryflavor.api.other;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class TabCommand {

    private int argument;
    private List<String> tabList;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     * @param argument
     * @param tabList
     */
    public TabCommand(int argument, List<String> tabList) {
        this.argument = argument;
        this.tabList = tabList;
    }

    /**
     * @param argument
     * @param tabList
     */
    public TabCommand(int argument, String... tabList) {
        this.argument = argument;
        this.tabList = Arrays.asList(tabList);
    }

    public int getArgument() { return this.argument; }
    public List<String> getTabList() { return this.tabList; }



}
