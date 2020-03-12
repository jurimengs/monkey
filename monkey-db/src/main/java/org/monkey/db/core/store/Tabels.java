package org.monkey.db.core.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.monkey.db.core.executor.Executor;
import org.monkey.db.core.store.synch.IncrDataLinkPointer;
import org.monkey.db.core.store.synch.IncrDataPointer;

public class Tabels {
    private static Tabels SINGLE = new Tabels();
    private byte[] bytelock = new byte[0];
    // 记录本节点需要向外同步数据的 节点， 及数据的起始指针
    private List<IncrDataPointer> incrDataPointerList = new ArrayList<>();
    
    private Map<String, Store> storeCache = new ConcurrentHashMap<>();
    
    public static Tabels getInstance() {
        return SINGLE;
    }
    
    public Store getStore(String tableName, Executor executor) {
        // return if contains it
        if(storeCache.containsKey(tableName)) {
            return storeCache.get(tableName);
        }
        
        // or will create one
        synchronized(bytelock) {
            // check exist again, 
            if(storeCache.containsKey(tableName)) {
                return storeCache.get(tableName);
            }
            Store store = StoreFactory.getInstance().createStore(executor, tableName);
            storeCache.put(tableName, store);
            return store;
        }
    }
    
    /**
     * 请求数据同步的存量数据 , 该方法不会被频繁调用
     * @Title: getStoreCacheClone
     * @param ip 请求数据同步的节点 ip
     * @return
     * @author zhouman
     * @date 2020-03-10 03:50:37
     */
    public Map<String, Store> getStoreCacheClone(String ip) {
        // 把 storeCache 锁住，对于数据世界来讲就是 STW
        // 这样就能确保 incrStartMap 与 storeCache 的copy 节点保持一致了吗？
        HashMap<String, Store> tmp = new HashMap<>();
        synchronized (storeCache) {
            // 再锁 incrStartMap， 这样读 incrStartMap 的也在等待。读 incrStartMap 的地方主要是 HashStore.add ， 
            // add 如果读不了，会造成 add 卡顿，直到 这两重锁释放
            // 也就是说， 在添加节点的时候，应该尽量选择空闲的时候
            
            pointStockPosition(ip);
            tmp.putAll(storeCache);
        }
        return tmp;
    }

    /**
     * 记录某个请求同步的节点，存量数据的位置
     * @Title: pointStockPosition
     * @Description: TODO(描述)
     * @param ip
     * @author zhouman
     * @date 2020-03-10 05:49:58
     */
    private void pointStockPosition(String ip) {
        Set<Entry<String, Store>> entrySet = storeCache.entrySet();
        for (Iterator<Entry<String, Store>> iterator = entrySet.iterator(); iterator.hasNext();) {
            Entry<String, Store> entry = iterator.next();
            String tableName = entry.getKey();
            Store store = entry.getValue();
            
            List<IncrDataLinkPointer> incrDataLinkPointers = parseFromStore(store);
            
            IncrDataPointer pointer = new IncrDataPointer();
            pointer.setIp(ip);
            pointer.setTableName(tableName);
            pointer.setIncrDataLinkPointers(incrDataLinkPointers);
            
            incrDataPointerList.add(pointer);
        }
    }

    private List<IncrDataLinkPointer> parseFromStore(Store store) {
        Link[] allLink = store.getAllLink();
        List<IncrDataLinkPointer> list = new ArrayList<>();
        for (int i = 0; i < allLink.length; i++) {
            IncrDataLinkPointer linkPointer = new IncrDataLinkPointer();
            linkPointer.setIncrPointer(allLink[i].size());
            linkPointer.setLink(allLink[i]);
            list.add(linkPointer);
        }
        return list;
    }
}
