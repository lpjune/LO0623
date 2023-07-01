package tool;

import enums.ToolTypeName;

import java.math.BigDecimal;

class Chainsaw extends Tool {

    Chainsaw(String code) {
        super(code);
    }

    @Override
    public ToolTypeName getToolTypeName() {
        return ToolTypeName.Chainsaw;
    }

    @Override
    public BigDecimal getDailyCharge() {
        return BigDecimal.valueOf(1.49);
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
        return true;
    }
}
