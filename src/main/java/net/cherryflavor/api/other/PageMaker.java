package net.cherryflavor.api.other;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * Created on 2/24/21
 * Time 7:23 PM
 */
public class PageMaker {

    public List<Object> valueList;
    public int amountPerPage;
    public Map<Integer, List<Object>> valuesSplitMap;
    public Integer numberOfPages;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     * PageMaker input entire list of values (must be string), then put a amountPerPage (how much per page)
     * @param valueList
     * @param amountPerPage
     */
    public PageMaker(List<Object> valueList, int amountPerPage) {
        this.valueList = valueList;
        this.amountPerPage = amountPerPage;
        this.valuesSplitMap = new HashMap<>();

        List<List<Object>> contentPages = Lists.partition(valueList, amountPerPage);

        int numberOfPages = 0;
        for (int i = 0; i < contentPages.size(); i++) {
            valuesSplitMap.put(i, contentPages.get(i));
            numberOfPages++;
        }
        this.numberOfPages = numberOfPages;
    }

    /**
     * PageMaker input entire list of values (must be string), then put a amountPerPage (how much per page)
     * @param amountPerPage
     */
    public PageMaker(int amountPerPage) {
        this.valueList = new ArrayList<>();
        this.amountPerPage = amountPerPage;
        this.valuesSplitMap = new HashMap<>();

        List<List<Object>> contentPages = Lists.partition(valueList, amountPerPage);

        int numberOfPages = 0;
        for (int i = 0; i < contentPages.size(); i++) {
            valuesSplitMap.put(i, contentPages.get(i));
            numberOfPages++;
        }
        this.numberOfPages = numberOfPages;
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * Get max number of pages
     * @return
     */
    public int getNumberOfPages() { return this.numberOfPages; }

    /**
     * Get list from page
     * @param pageNumber
     * @return
     */
    public List<Object> getPage(int pageNumber) {
        return valuesSplitMap.get(pageNumber);
    }

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Adds data
     * @param pageEntry
     */
    public void addData(Object... pageEntry) {
        for (Object entry : pageEntry) {
            valueList.add(entry);
        }
        refreshPages();
    }

    /**
     * Refreshes pages
     */
    public void refreshPages() {
        List<List<Object>> contentPages = Lists.partition(valueList, amountPerPage);

        int numberOfPages = 0;
        for (int i = 0; i < contentPages.size(); i++) {
            valuesSplitMap.put(i, contentPages.get(i));
            numberOfPages++;
        }
        this.numberOfPages = numberOfPages;
    }
}
