package org.skoal.restrictor.test;

import org.apache.commons.lang3.RandomUtils;
import org.skoal.restrictor.utils.Lambda;

public class Test1 {
    public static void main(String[] args) {
        Lambda.repeat(10,
                () -> System.out.println(RandomUtils.nextInt(3, 5)));
    }
}
