package org.monkey.db.core.store;

import com.alibaba.fastjson.JSONArray;

public class Link<K> {
    private byte[] lock = new byte[1];
    private int size;
    private int hash; // 对应到数组的下标, 一条链上的所有 hash 值都是一样的
    private StoreNode<K> last;
    private StoreNode<K> first;

    public Link(int hash) {
        this.hash = hash;
    }

    public int size() {
        return size;
    }

    /**
     * 线程不安全的
     */
    public void add(K key, Object newValue) {
        // System.out.println("this:" + JSON.toJSONString(this));
        StoreNode<K> next = new StoreNode<>(key, newValue, null);
        synchronized (lock) {
            // 用内部对象锁，可以在保证增量变化安全的前提下，避免对对象的其他方法造成影响 
            if (this.last == null) {
                // if last is null , means the first must be null, so do init first
                this.first = next;
            } else {
                // if last is not null, give next to it.
                this.last.setNext(next);
                next.setPrev(this.last);
            }
            // then give cusor of this.last to next
            this.last = next;
            this.size ++;
        }
    }

    public StoreNode<K> getNodeByKey(K key) {
        StoreNode<K> node = this.getFirst();
        while (node != null && !node.getKey().equals(key)) {
            // 直到找到 key 不相同的 node 
            node = node.getNext();
        }
        return node;
    }

    public void remove(K key) {
        StoreNode<K> node = getNodeByKey(key);
        if(node != null) {
            synchronized (lock) {
                // 如果有 next, 则取next
                // 如果没有 next, 表示它自己就是最后一个
                // 如果有 prev， 则取 prev， 
                // 如果没有 prev， 则表示它自己就是第一个
                StoreNode<K> prev = node.getPrev();
                StoreNode<K> next = node.getNext();
                if(prev != null) {
                    if(next != null) {
                        // 前不为空后不为空， 就将其接起来， first 、 last 都不动
                        prev.setNext(next);
                        next.setPrev(prev);
                    } else {
                        // 前不为空，后为空，表示自己是最后一个, 移除后 prev 的next 指向null， first 不变, last 指向 prev
                        prev.setNext(null);
                        this.last = prev;
                    }
                } else {
                    if(next != null) {
                        // 前为空后不为空表示自己就是第一个，last 不动
                        next.setPrev(null);
                        this.first = next;
                    } else {
                        // 前为空后为空表示自己就是第一个，也是最后一个。移除后， 链就空了
                        this.last = null;
                        this.first = null;
                    }
                }
                node = null;
                // then give cusor of this.last to next
                this.size --;
            }
        }
    }
    
    public JSONArray toJSONArray() {
        JSONArray tmp = new JSONArray();
        StoreNode<K> tmpNode = this.first;
        if (tmpNode != null) {
            tmp.add(tmpNode.toJSON());
            while((tmpNode = tmpNode.getNext()) != null) {
                tmp.add(tmpNode.toJSON());
            }
        }
        return tmp;
    }

    public StoreNode<K> getLast() {
        return last;
    }

    public void setLast(StoreNode<K> last) {
        this.last = last;
    }

    public StoreNode<K> getFirst() {
        return first;
    }

    public void setFirst(StoreNode<K> first) {
        this.first = first;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

}
