package org.skoal.restrictor.restrictor;

public interface Restrictor {
    boolean tryAcquire(String clientId, String api);
}
