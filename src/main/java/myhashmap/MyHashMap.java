package myhashmap;

import java.util.Objects;

public class MyHashMap<K,V> {
    private Node<K,V>[] table;
    private int size;
    private int capacity;
    private float loadFactor;
    private int threshold;

    public MyHashMap() {
        capacity = 16;
        loadFactor = 0.75f;
        threshold = 12;
        table = (Node<K,V>[]) new Node[capacity];
    }

    static int hash(Object key) {
        if(key == null) return 0;
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }

    public V put(K key, V value) {
        int hash = MyHashMap.hash(key);
        int index = (capacity - 1) & hash;
        if (table[index] == null) {
            table[index] = new Node<>(hash, key, value, null);
            size++;
            if (size > threshold) resize();
            return null;
        }

        Node<K,V> current = table[index];
        while (true) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            if (current.nextNode == null) {
                current.nextNode = new Node<>(hash, key, value, null);
                size++;
                if (size > threshold) resize();
                return null;
            }
            current = current.nextNode;
        }
    }

    public V get(Object key) {
        int hash = MyHashMap.hash(key);
        int index = (capacity - 1) & hash;
        Node<K,V> current = table[index];
        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.nextNode;
        }
        return null;
    }

    public V remove(Object key) {
        int hash = MyHashMap.hash(key);
        int index = (capacity - 1) & hash;
        Node<K,V> current = table[index];
        Node<K,V> prev = null;

        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                V oldValue = current.value;

                if (prev == null) {
                    table[index] = current.nextNode;
                } else {
                    prev.nextNode = current.nextNode;
                }
                size--;
                return oldValue;
            }
            prev = current;
            current = current.nextNode;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        Node<K,V>[] oldTable = table;
        int oldCapacity = capacity;
        capacity = oldCapacity * 2;
        threshold = (int)(capacity * loadFactor);
        table = (Node<K,V>[]) new Node[capacity];

        for (int i = 0; i < oldCapacity; i++) {
            Node<K,V> current = oldTable[i];
            while (current != null) {
                Node<K,V> next = current.nextNode;
                int newIndex = (capacity - 1) & current.hash;
                current.nextNode = table[newIndex];
                table[newIndex] = current;
                current = next;
            }
        }
    }

    static class Node<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> nextNode;

        public Node(int hash, K key, V value, Node<K, V> nextNode) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.nextNode = nextNode;
        }
    }
}