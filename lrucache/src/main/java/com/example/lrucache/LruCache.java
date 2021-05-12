package com.example.lrucache;

import java.util.HashMap;

class LruCache {

    static class DLinkedNode {
        int key;
        int value;
        DLinkedNode pre;
        DLinkedNode next;

        public DLinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    int capacity;
    private final HashMap<Integer, DLinkedNode> hashMap;
    private DLinkedNode head;
    private DLinkedNode tail;

    public LruCache(int capacity) {
        this.capacity = capacity;
        hashMap = new HashMap<>(capacity);
        head = new DLinkedNode(-1, -1);
        tail = new DLinkedNode(-1, -1);
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        DLinkedNode node = hashMap.get(key);
        if (node == null) {
            return -1;
        }
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = hashMap.get(key);
        if (node != null) {
            node.value = value;
            moveToHead(node);
        } else {
            node = new DLinkedNode(key, value);
            if (hashMap.size() < capacity) {
                hashMap.put(key, node);
                addToHead(node);
            } else {
                DLinkedNode tailNode = removeTail();
                addToHead(node);
                hashMap.remove(tailNode.key);
                hashMap.put(key, node);
            }
        }
    }

    public void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    public void removeNode(DLinkedNode node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    public void addToHead(DLinkedNode node) {
        node.next = head.next;
        node.pre = head;
        head.next.pre = node;
        head.next = node;
    }

    public DLinkedNode removeTail() {
        DLinkedNode last = tail.pre;
        removeNode(last);
        return last;
    }

    public static void main(String[] args) {
        LruCache lruCache = new LruCache(2);
        int value = 0;
        lruCache.put(1,1);
        lruCache.put(2,2);
        value = lruCache.get(1);
        lruCache.put(3,3);
        value =lruCache.get(2);
        lruCache.put(4,4);
        value = lruCache.get(1);
        value = lruCache.get(3);
        value = lruCache.get(4);
    }
}
