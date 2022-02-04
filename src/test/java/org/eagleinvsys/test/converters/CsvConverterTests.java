package org.eagleinvsys.test.converters;

import org.eagleinvsys.test.converters.impl.ConvertibleCollectionImpl;
import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvConverterTests {

    CsvConverter underTest;
    ConvertibleCollection testCollection;
    String expectedResponse;
    PrintStream testOut;
    ByteArrayOutputStream outputStreamCaptor;

    @Before
    public void setUp(){
        Map<String, String> firstMap = new HashMap<>();
        firstMap.put("testHeader 1", "testValue1Header1");
        firstMap.put("testHeader 2", "testValue1Header2");
        firstMap.put("testHeader 3", "testValue1Header3");
        firstMap.put("testHeader 4", "testValue1Header4");

        testCollection = new ConvertibleCollectionImpl(firstMap);

        List<String> expectedValues = firstMap.values().
                stream().
                sorted().
                collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < expectedValues.size(); i++){
            sb.append(expectedValues.get(i));
            if ((i + 1) == expectedValues.size()) {
                break;
            }
            sb.append(",");
        }
        sb.append("\r\n");

        expectedResponse = sb.toString();
        outputStreamCaptor = new ByteArrayOutputStream();
        testOut = new PrintStream(outputStreamCaptor);
        underTest = new CsvConverter();
    }

    @Test
    public void canConvertData(){
        underTest.convert(testCollection,testOut);
        assertThat(outputStreamCaptor.toString()).isEqualTo(expectedResponse);
    }
}
