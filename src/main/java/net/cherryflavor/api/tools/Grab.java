package net.cherryflavor.api.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Grab {

    public static <T> T firstEntryWithHighestValue(final Map<T, Integer> map) {
        T currentMaxKey = null;
        int maxValue = -1;
        for (final Entry<T, Integer> entry : map.entrySet()) {
          final T key = entry.getKey();
          final Integer value = entry.getValue();
          if (value > maxValue) {
            currentMaxKey = key;
            maxValue = value;
          }
        }
        return currentMaxKey;
      }
    
    
    // This returns the last key that has the highest value
    
      public static  <T> T lastEntryWithHighestValue(final Map<T, Integer> map) {
        T currentMaxKey = null;
        int maxValue = -1;
        for (final Entry<T, Integer> entry : map.entrySet()) {
          final T key = entry.getKey();
          final Integer value = entry.getValue();
          if (value >= maxValue) { // Changed this line here
            currentMaxKey = key;
            maxValue = value;
          }
        }
        return currentMaxKey;
      }
    
    
    // This returns all keys with the highest value
      public static  <T> List<T> allEntriesWithHighestValue(final Map<T, Integer> map) {
        final List<T> maxKeyEntries = new ArrayList<>();
        int maxValue = -1;
        for (final Entry<T, Integer> entry : map.entrySet()) {
          final T key = entry.getKey();
          final Integer value = entry.getValue();
          if (value > maxValue) {
            maxValue = value;
            maxKeyEntries.clear();
          }
          if (value == maxValue) {
            maxKeyEntries.add(key);
          }
        }
        return maxKeyEntries;
      }


    //Gets most accomodating size for inventory based off number of the items in the inventory
      public static int accomodatingInventorySize(int size) {
        System.out.println(Math.ceil(((double) size)/9) * 9);
        return (int) Math.ceil(((double) size)/9) * 9;
      }
    
}
