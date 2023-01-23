package com.shopme.admin.utils;

import com.shopme.common.entity.FinancialYear;

public class FinancialYearHolder {
    private static ThreadLocal<FinancialYear> financialYear = new ThreadLocal<>();

    public static void set(FinancialYear fy) {
        financialYear.set(fy);
    }

    public static FinancialYear get() {
        return financialYear.get();
    }

    public static void clear() {
        financialYear.remove();
    }
}
