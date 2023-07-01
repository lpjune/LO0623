package main.tool;

import enums.ToolTypeName;

import java.math.BigDecimal;

class Ladder extends Tool {

    Ladder(String code) {
        super(code);
    }

    @Override
    public ToolTypeName getToolTypeName() {
        return ToolTypeName.Ladder;
    }

    @Override
    public BigDecimal getDailyCharge() {
        return BigDecimal.valueOf(1.99);
    }

    @Override
    public Boolean getIsWeekdayCharge() {
        return true;
    }

    @Override
    public Boolean getIsWeekendCharge() {
        return true;
    }

    @Override
    public Boolean getIsHolidayCharge() {
        return false;
    }
}
