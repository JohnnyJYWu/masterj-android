package com.masterj.base.data;

public class DataWithExpiration<T> extends BaseData {

    private T data;
    private long timestamp;
    private int cacheVersion;  // hash code of all versions, < 0 no version related

    public DataWithExpiration() {
        timestamp = -1;
        cacheVersion = -1;
    }

    public DataWithExpiration(T data) {
        this(data, -1);
    }

    public DataWithExpiration(T data, int cacheVersion) {
        this();
        setDataUpdatingTimestamp(data);
        this.cacheVersion = cacheVersion;
    }

    public DataWithExpiration(T data, long timestamp) {
        this.data = data;
        this.timestamp = timestamp;
        this.cacheVersion = -1;
    }

    public T getData(long expiration) {
        return getData(expiration, -1);
    }

    public T getData(int newCacheVersion) {
        return getData(-1, newCacheVersion);
    }

    public T getData(long expiration, int newCacheVersion) {
        T ret = data;
        if (expiration >= 0) {
            long current = System.currentTimeMillis();
            if (current > (timestamp + expiration)) {
                ret = null;
            }
        }
        // may be the data just turn to version related!
        if (newCacheVersion != cacheVersion) {
            ret = null;
        }
        return ret;
    }

    public T getData(long expiration, long current, int newCacheVersion) {
        T ret = data;
        if (expiration >= 0) {
            if (current > (timestamp + expiration)) {
                ret = null;
            }
        }
        // may be the data just turn to version related!
        if (newCacheVersion != cacheVersion) {
            ret = null;
        }
        return ret;
    }

    public void setDataUpdatingTimestamp(T data) {
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public void setDataUpdatingCacheVersion(T data, int cacheVersion) {
        setDataUpdatingTimestamp(data);
        this.cacheVersion = cacheVersion;
    }
}
