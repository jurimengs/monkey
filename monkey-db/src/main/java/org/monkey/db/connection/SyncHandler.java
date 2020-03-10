package org.monkey.db.connection;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.monkey.base.DataModelIn;

@Component
public class SyncHandler {

    public void executeSych(Map<String, Boolean> incrSychClusterIp, DataModelIn dataModelIn) {
        Set<Entry<String, Boolean>> entrySet = incrSychClusterIp.entrySet();
        for (Iterator<Entry<String, Boolean>> iterator = entrySet.iterator(); iterator.hasNext();) {
            Entry<String, Boolean> entry = iterator.next();
            String ip = entry.getKey();
            Boolean isSycn = entry.getValue();
            if(isSycn) {
                synchronizeTo(ip, dataModelIn);
            }
        }
    }

    private void synchronizeTo(String ip, DataModelIn dataModelIn) {
        // TODO Auto-generated method stub
        
    }
}
