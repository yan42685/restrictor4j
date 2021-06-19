package org.skoal.restrictor.config.enums;

/**
 * trie相比hashmap，占用空间较少，耗时较多（约2-5倍的差距）
 */
public enum RuleStructure {
    HASH_MAP, TRIE
}
