package net.cherryflavor.api.other;

import java.util.Arrays;
import java.util.List;

public class TabCommand {

    private int argument;
    private List<String> tabList;

    public TabCommand(int argument, List<String> tabList) {
        this.argument = argument;
        this.tabList = tabList;
    }

    public TabCommand(int argument, String... tabList) {
        this.argument = argument;
        this.tabList = Arrays.asList(tabList);
    }

    public int getArgument() { return this.argument; }
    public List<String> getTabList() { return this.tabList; }



}
