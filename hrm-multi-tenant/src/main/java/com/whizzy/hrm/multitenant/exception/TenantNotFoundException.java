package com.whizzy.hrm.multitenant.exception;

import java.io.Serial;

public class TenantNotFoundException  extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -783431806928578821L;

    public TenantNotFoundException(String message) {
        super(message);
    }
}