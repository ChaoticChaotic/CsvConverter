package org.eagleinvsys.test.converters;

import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.converters.impl.StandardCsvConverter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class StandardCsvConverterTests {

    StandardCsvConverter underTest;
    CsvConverter mockConverter;
    List<Map<String, String>> collectionToTest;
    String expectedResponse;
    PrintStream testOut;
    ByteArrayOutputStream outputStreamCaptor;


    @Before
    public void setUp() {
        Map<String, String> firstMap = new HashMap<>();

        firstMap.put("testHeader 1", "testValue1Header1");
        firstMap.put("testHeader 2", "testValue1Header2");
        firstMap.put("testHeader 3", "testValue1Header3");
        firstMap.put("testHeader 4", "testValue1Header4");

        collectionToTest = new ArrayList<>();
        collectionToTest.add(firstMap);
        TreeSet<String> headers = new TreeSet<>(firstMap.keySet());
        List<String> expectedHeaders = new ArrayList<>(headers);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < expectedHeaders.size(); i++){
            sb.append(expectedHeaders.get(i));
            if ((i + 1) == expectedHeaders.size()) {
                break;
            }
            sb.append(",");
        }
        sb.append("\r\n");

        expectedResponse = sb.toString();
        outputStreamCaptor = new ByteArrayOutputStream();
        testOut = new PrintStream(outputStreamCaptor);
        mockConverter = Mockito.mock(CsvConverter.class);
        underTest = new StandardCsvConverter(mockConverter);
    }

    @Test
    public void canConvert() {
        underTest.convert(collectionToTest,testOut);
        assertThat(outputStreamCaptor.toString()).isEqualTo(expectedResponse);
    }

    @Test
    public void tryConvertDifferentHeadersThenIllegalArgException(){

        Map<String, String> secondMap = new HashMap<>();
        secondMap.put("header 5", "value5forheader1");
        secondMap.put("header 6", "value6forheader2");
        secondMap.put("header 7", "value7forgeader3");
        secondMap.put("header 8", "value8forheader4");
        collectionToTest.add(secondMap);

        assertThatThrownBy(() -> underTest.convert(collectionToTest,testOut))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid Data! Headers are not the same!");
    }
}