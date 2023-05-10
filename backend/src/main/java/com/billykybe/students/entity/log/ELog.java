package com.billykybe.students.entity.log;

public enum ELog {

    // Account logs
    ACCOUNT_VERIFICATION,
    ACCOUNT_REGISTRATION,
    ACCOUNT_LOGOUT,
    ACCOUNT_LOGIN,
    FORGOT_PASSWORD_REQUEST,
    FORGOT_PASSWORD_RESET,
    CHANGE_PASSWORD,

    // System logs
    SYS_CREATE,
    SYS_READ,
    SYS_UPDATE,
    SYS_DELETE,
    SYS_ERROR,
    SYS_REQUEST_FAIL,
    SYS_REQUEST_PASS


}
