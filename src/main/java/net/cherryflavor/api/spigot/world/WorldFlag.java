package net.cherryflavor.api.spigot.world;

import java.util.ArrayList;
import java.util.List;

public enum WorldFlag {

    NO_FALL_DAMAGE("No Fall Damage", "Disables Fall Damage", "NOFALLDAMAGE"),
    NO_HOSTILE_MOBS("No Hostile Mobs", "Disables Hostile Mobs", "NOHOSTILEMOBS"),
    NO_PASSIVE_MOBS("No Passive Mobs", "Disables Passive Mobs", "NOPASSIVEMOBS"),
    NO_PVP("No PVP", "Disables PVP in world", "NOPVP"),
    BLOCK_PLACE("No Blocks Placed", "Prevents Block Placement", "BLOCKPLACE"),
    BLOCK_DESTROY("No Blocks Destroyed", "Prevents Block Destroyed", "BLOCKDESTROY"),
    NO_ITEM_DROP("No Item Drops", "Prevents Item Drops", "NOITEMDROP");

    String label;
    String description;
    String configTag;

    WorldFlag(String label, String description, String configTag) {
        this.label = label;
        this.description = description;
        this.configTag = configTag;
    }

    /**
     *
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public String getConfigTag() {
        return configTag;
    }

    public static WorldFlag parseByConfigTag(String string) {
        for (WorldFlag flag : values()) {
            if (flag.getConfigTag().equalsIgnoreCase(string)) {
                return flag;
            }
        }
        return null;
    }


    public static List<String> convertToConfigList(List<WorldFlag> worldFlags) {
        List<String> configList = new ArrayList<>();
        for (WorldFlag flag : worldFlags) {
            configList.add(flag.getConfigTag());
        }
        return configList;
    }

    public static List<WorldFlag> parseStringList(List<String> worldFlags) {
        List<WorldFlag> worldFlagList = new ArrayList<>();
        for (String flag : worldFlags) {
            for (WorldFlag worldFlag : WorldFlag.values()) {
                if (worldFlag.configTag.equalsIgnoreCase(flag)) {
                    worldFlagList.add(worldFlag);
                }
            }
        }
        return worldFlagList;
    }
}
