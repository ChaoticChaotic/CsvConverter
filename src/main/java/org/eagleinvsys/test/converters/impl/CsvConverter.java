package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.Converter;
import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;
import org.eagleinvsys.test.escscreener.EscScreener;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class CsvConverter implements Converter {

    /**
     * Converts given {@link ConvertibleCollection} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    public void convert(ConvertibleCollection collectionToConvert, OutputStream outputStream) {
        ArrayList<String> headers = (ArrayList<String>) collectionToConvert.getHeaders();
        List<ConvertibleMessage> messages  = (List<ConvertibleMessage>) collectionToConvert.getRecords();
        PrintWriter writer = new PrintWriter(outputStream);
        for (int i = 0; i < messages.size(); i++) {
            writer.write(EscScreener.screenString(messages.
                    get(i).
                    getElement(headers.get(i)))
            );
            if ((i + 1) != messages.size()){
                writer.write(",");
            }
        }
        writer.write("\r\n");
        writer.flush();
    }

}