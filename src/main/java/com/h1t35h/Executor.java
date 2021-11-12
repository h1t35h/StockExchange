package com.h1t35h;

import java.io.IOException;

/**
 * Class for static execution
 */
public class Executor {

    public static void main(String[] args) throws IOException {
        StockExchange stockExchange = new StockExchange();
        String filePath = args[0];
        stockExchange.main(new String[]{filePath});
    }
}
