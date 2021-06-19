package org.skoal.restrictor.basic.datastructure;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Trie<T> {
    private final Node<T> root = new Node<T>();

    private static class Node<T> {
        T value;
        boolean isBreakPoint = false;
        Map<Character, Node<T>> map = new HashMap<>();
    }

    public boolean insert(String key, T value) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        Node<T> pre;
        Node<T> curr = root;
        for (char c : key.toCharArray()) {
            pre = curr;
            curr = curr.map.get(c);
            if (curr == null) {
                curr = new Node<T>();
                pre.map.put(c, curr);
            }
        }
        curr.value = value;
        curr.isBreakPoint = true;
        return true;
    }

    public boolean delete(String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        char firstRedundantChar = key.charAt(0);
        Node<T> pre = root;
        Node<T> curr = root;
        char[] chars = key.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            curr = curr.map.get(chars[i]);
            // 要删除的key不存在
            if (curr == null || (i == chars.length - 1 && !curr.isBreakPoint)) {
                return false;
            }
            // 令pre指向上一个isBreakPoint节点
            if (i != chars.length - 1 && curr.isBreakPoint) {
                firstRedundantChar = chars[i + 1];
                pre = curr;
            }
        }
        // 既是breakPoint又是叶子结点, 就从倒数第二个BreakPoint开始删除
        if (curr.map.isEmpty()) {
            pre.map.remove(firstRedundantChar);
        } else {
            // 否则是中间breakPoint，只删除data和isBreakPoint
            curr.value = null;
            curr.isBreakPoint = false;
        }
        return true;
    }

    public boolean startsWith(String prefix) {
        Node<T> lastNode = getLastMatchNode(prefix);
        return lastNode != null;
    }

    public boolean containsKey(String key) {
        Node<T> lastNode = getLastMatchNode(key);
        return lastNode != null && lastNode.isBreakPoint;
    }

    public T get(String key) {
        Node<T> lastNode = getLastMatchNode(key);
        if (lastNode != null && lastNode.isBreakPoint) {
            return lastNode.value;
        }
        return null;
    }

    /**
     * 如果word中的字符全部匹配，则返回最后一个匹配的Node，否则返回null
     */
    private Node<T> getLastMatchNode(String key) {
        Node<T> curr = root;
        for (char c : key.toCharArray()) {
            curr = curr.map.get(c);
            if (curr == null) {
                return null;
            }
        }
        return curr;
    }
}
