package com.project.retail_store.util;

import com.project.retail_store.exception.RetailStoreException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtils {


    public static RetailStoreException logAndGetException(String errorMessage, Exception e) {
        log.error(errorMessage, e);
        return new RetailStoreException(errorMessage);
    }

    public static RetailStoreException logAndGetException(String errorMessage) {
        log.error(errorMessage);
        return new RetailStoreException(errorMessage);
    }
}