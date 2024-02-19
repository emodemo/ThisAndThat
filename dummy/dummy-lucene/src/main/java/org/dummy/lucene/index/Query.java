package org.dummy.lucene.index;

import java.util.List;

public class Query {

    public boolean canSplit(List<String> dictionary, String query){
        return doCanSplit(dictionary, query);
    }

    private boolean doCanSplit(List<String> dictionary, String query){
        if(query == null || query.isBlank())
            return true;
        int length = query.toCharArray().length;
        for(int i = 1; i <= length; i++){
            var prefix = query.substring(0, i);
            var suffix = query.substring(i, length);
            if (dictionary.contains(prefix) && doCanSplit(dictionary, suffix))
                return true;
        }
        return false;
    }
}
