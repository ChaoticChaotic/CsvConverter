package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.StandardConverter;
import org.eagleinvsys.test.escscreener.EscScreener;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StandardCsvConverter implements StandardConverter {

    private final CsvConverter csvConverter;

    public StandardCsvConverter(CsvConverter csvConverter) {
        this.csvConverter = csvConverter;
    }

    /**
     * Converts given {@link List<Map>} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format. All maps must have the same set of keys
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    public void convert(List<Map<String, String>> collectionToConvert, OutputStream outputStream) {
        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(collectionToConvert.get(0));
        List<String> headers = (List<String>) convertibleCollection.getHeaders();

        for(Map<String,String> mapToValidate : collectionToConvert){
            Set<String> tmpHeaders = mapToValidate.keySet();
            List<String> screenedHeaders = EscScreener.screenCollection(new ArrayList<>(tmpHeaders));
            if (!(headers.containsAll(screenedHeaders))) {
                throw new IllegalArgumentException("Invalid Data! Headers are not the same!");
            }
        }

        try(PrintWriter writer = new PrintWriter(outputStream)) {
            for (int i = 0; i < headers.size(); i++) {
                writer.write(headers.get(i));
                if ((i + 1) != headers.size()) {
                    writer.write(",");
                }
            }
            writer.write("\r\n");
            writer.flush();
            for (Map<String,String> next: collectionToConvert){
                ConvertibleCollection any = new ConvertibleCollectionImpl(next);
                csvConverter.convert(any, outputStream);
            }
        }
    }

}