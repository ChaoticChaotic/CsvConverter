package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;
import org.eagleinvsys.test.escscreener.EscScreener;

import java.util.*;

public class ConvertibleCollectionImpl implements ConvertibleCollection {

    Map<String,String> rawCollection;

    public ConvertibleCollectionImpl(Map<String, String> rawCollection) {
        this.rawCollection = rawCollection;
    }

    @Override
    public Collection<String> getHeaders() {
        TreeSet<String> rawHeaders = new TreeSet<>(rawCollection.keySet());
        List<String> headers = new ArrayList<>(rawHeaders);
        return EscScreener.screenCollection(headers);
    }

    @Override
    public Iterable<ConvertibleMessage> getRecords() {
        List<ConvertibleMessage> massages = new ArrayList<>();
        TreeSet<String> tmpHeaders = new TreeSet<>(rawCollection.keySet());
        List<String> headers = new ArrayList<>(tmpHeaders);
        for(String header: headers){
            ConvertibleMessageImpl message = new ConvertibleMessageImpl();
            message.setElementId(header);
            message.setValue(rawCollection.get(header));
            massages.add(message);
        }
        return massages;
    }
}
