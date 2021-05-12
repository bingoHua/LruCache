package com.example.lrucache;

import java.util.HashMap;

class LRUCache {

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

    public LRUCache(int capacity) {
        this.capacity = capacity;
        hashMap = new HashMap<>(capacity);
        head = new DLinkedNode(-1, -1);
        tail = new DLinkedNode(-1, -1);
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        return hashMap.get(key).value;
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
                hashMap.remove(tailNode.key);
                hashMap.put(key, tailNode);
            }
        }
    }

    public void moveToHead(DLinkedNode node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
        node.pre = null;
        node.next = head;
        head = node;
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
        tail = tail.pre;
        removeNode(tail);
        return tail;
    }

}
