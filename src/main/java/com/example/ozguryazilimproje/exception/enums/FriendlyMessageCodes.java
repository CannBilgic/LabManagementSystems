package com.example.ozguryazilimproje.exception.enums;

public enum FriendlyMessageCodes  implements  IFriendlyMessageCode{
    OK(1000),
    ERROR(1001),
    SUCCESS(1002),
    IMAGE_NOT_SAVE_EXCEPTION(1500),
    IMAGE_NOT_FOUND_EXCEPTION(1501),
    IMAGE_SUCCESSFULLY_SAVED(1502),
    IMAGE_SUCCESSFULLY_DELETE(1503),
    ROLE_NOT_CREATED_EXCEPTION(1504),
    ROLE_NOT_FOUND_EXCEPTION(1505),
    ROLE_NOT_DELETED_EXCEPTION(1506),
    ROLE_SUCCESSFULLY_CREATED(1507),
    ROLE_SUCCESSFULLY_DELETED(1508),
    LABORANT_NOT_CREATED_EXCEPTION(1509),
    LABORANT_NOT_FOUND_EXCEPTION(1510),
    LABORANT_SUCCESSFULLY_CREATED(1511),
    LABORANT_SUCCESSFULLY_DELETED(1512),
    LABORANT_SUCCESSFULLY_UPDATED(1517),
    PATIENT_NOT_FOUND_EXCEPTION(1513),
    PATIENT_NOT_CREATED_EXCEPTION(1514),
    PATIENT_SUCCESSFULLY_CREATED(1515),
    PATIENT_SUCCESSFULLY_DELETED(1516),
    PATIENT_SUCCESSFULLY_UPDATED(1518),

    REPORT_NOT_FOUND_EXCEPTION(1519),
    REPORT_NOT_CREATED_EXCEPTION(1520),
    REPORT_SUCCESSFULLY_CREATED(1521),
    REPORT_SUCCESSFULLY_DELETED(1522),
    REPORT_SUCCESSFULLY_UPDATED(1523),
    ;
    private  final  int value;

     FriendlyMessageCodes(int value){
        this.value=value;
    }

    @Override
    public int getFriendlyMessageCode() {
        return value;
    }
}
