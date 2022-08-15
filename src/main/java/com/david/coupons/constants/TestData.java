package com.david.coupons.constants;
import com.david.coupons.enums.Category;

import java.sql.Date;

public class TestData {
    //General test texts and logs:
    
    //Admin test variables
    public final static String ADMIN_LOGIN_EMAIL = "admin@admin.com";
    public final static String ADMIN_LOGIN_PASSWORD = "admin";


    public final static String COMPANY_EMAIL = "mycompany1@gmail.com";
    public final static String COMPANY_EMAIL_UPDATE = "mycompany2@gmail.com";
    public static final String COMPANY_PASSWORD = "12345";
    public static final String COMPANY_NAME = "myCompany";
    public static final String COMPANY_NAME_UPDATE = "myCompany2";
    public static final long COMPANY_ID = 1;
    public static final String CUSTOMER_EMAIL = "mycustomer@gmail.com";
    public static final String CUSTOMER_EMAIL_UPDATE = "mycustomer_update21@gmail.com";
    public static final String CUSTOMER_FIRST_NAME = "David";
    public static final String CUSTOMER_LAST_NAME = "Menachem";
    public static final String CUSTOMER_PASSWORD = "56789";
    public static final String CUSTOMER_FIRST_NAME_UPDATE ="Moshe";
    public static final String CUSTOMER_LAST_NAME_UPDATE ="Lonny";
    public static final long CUSTOMER_ID = 1;
    public static final int COUPON_AMOUNT = 20;
    public static final Category COUPON_CATEGORY = Category.RESTAURANT;
    public static final String COUPON_TITLE = "Bennidict";
    public static final Date COUPON_EXPIRE_DATE = new Date(System.currentTimeMillis() + 5000000);
    public static final Date COUPON_START_DATE = new Date(System.currentTimeMillis());
    public static final String COUPON_DESCRIPTION = "The best restaurant ever";
    public static final long COUPON_COMPANY_ID = 1;
    public static final long COUPON_ID = 2;
    public static final double COUPON_PRICE = 34.9;
    public static final double COUPON_PRICE_UPDATE = 40;
    public static final String COUPON_IMAGE = "url";
}
