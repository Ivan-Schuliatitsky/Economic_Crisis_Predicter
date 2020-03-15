package com.window;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scripts {

    public static void createCSV(List<String[]> data) throws IOException {
        String csv = "src/main/java/com/nado/data.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv), ',',CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        String[] header = { "case","cc3","country","year","systemic_crisis","exch_usd","domestic_debt_in_default","sovereign_external_debt_default","gdp_weighted_default","inflation_annual_cpi","independence","currency_crises","inflation_crises"};
        writer.writeNext(header);
        writer.writeAll(data);

        writer.close();
    }
}
