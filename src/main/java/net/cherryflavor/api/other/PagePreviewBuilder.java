package net.cherryflavor.api.other;

import net.cherryflavor.api.tools.TextFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/7/2021
 * Time 1:36 PM
 */

public class PagePreviewBuilder {

    private int lineLength = 64;

    private List<String> header;
    private List<String> footer;
    private PageMaker pageMaker;
    private String numberFormat;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    public PagePreviewBuilder(PageMaker pageMaker) {
        this.pageMaker = pageMaker;
        this.header = new ArrayList<>();
        this.footer = new ArrayList<>();
        this.numberFormat = "&e#%n%";
    }

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

    /**
     * Add header
     * @param addition
     */
    public void addHeader(String... addition) {
        for (String a : addition) {
            int i = (lineLength - a.length()) / 2;
            header.add(TextFormat.addLeftPadding(a, '_', i));
        }
    }

    /**
     * Add footer
     * @param addition
     */
    public void addFooter(String... addition) {
        for (String a : addition) {
            int i = (lineLength - a.length()) / 2;
            footer.add(TextFormat.addLeftPadding(a, '_', i));
        }
    }

    /**
     * %n% = to number
     * @param numberFormat
     */
    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

    /**
     * Use %page% to preview page number, %max% to preview max # of pages
     * @param page
     */
    public List<String> build(int page, boolean numberEachLine) {
        List<String> preview = new ArrayList<>();
        if (page > pageMaker.getNumberOfPages()) {
            throw new IndexOutOfBoundsException("Page request larger than number of pages");
        } else {
            for (String top : header) {
                preview.add(top.replaceAll("%page%", String.valueOf(page+1)).replaceAll("%max%", String.valueOf(pageMaker.getNumberOfPages())).replace('_',' '));
            }
            int n = 0;
            if (page == 0) {
              n = 0;
            } else {
                n = page * 10;
            }
            for (Object data : pageMaker.getPage(page)) {
                String value = (String) data;
                n++;
                if (numberEachLine == true) {
                    preview.add(numberFormat.replaceAll("%n%", String.valueOf(n)) + value.replaceAll("%page%", String.valueOf(page+1)).replaceAll("%max%", String.valueOf(pageMaker.getNumberOfPages())).replace('_',' '));
                } else {
                    preview.add(value.replaceAll("%page%", String.valueOf(page+1)).replaceAll("%max%", String.valueOf(pageMaker.getNumberOfPages())).replace('_',' '));
                }
            }
            for (String foot : footer) {
                preview.add(foot.replaceAll("%page%", String.valueOf(page+1)).replaceAll("%max%", String.valueOf(pageMaker.getNumberOfPages())).replace('_',' '));
            }
        }
        return preview;
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    //==================================================================================================================
    // SETTERS
    //==================================================================================================================


}
