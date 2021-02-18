package net.cherryflavor.api.bungee;

import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCherry extends Plugin {

    public BungeeCherry plugin;
    public ProxyAPI proxyAPI;

    public void onEnable() {
        plugin = this;
        proxyAPI = new ProxyAPI(this);

    }

    public void onDisable() {

    }


}
