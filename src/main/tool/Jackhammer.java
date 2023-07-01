package main.tool;

import enums.ToolTypeName;

import java.math.BigDecimal;

class Jackhammer extends Tool {

    Jackhammer(String code) {
        super(code);
    }

    @Override
    public ToolTypeName getToolTypeName() {
        return ToolTypeName.Jackhammer;
    }

    @Override
    public BigDecimal getDailyCharge() {
        return BigDecimal.valueOf(2.99);
    }

    @Override
    public Boolean getIsWeekdayCharge() {
        return true;
    }

    @Override
    public Boolean getIsWeekendCharge() {
        return false;
    }

    @Override
    public Boolean getIsHolidayCharge() {
        return false;
    }
}
