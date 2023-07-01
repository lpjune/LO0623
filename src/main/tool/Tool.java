package tool;

import enums.ToolBrand;
import enums.ToolTypeName;

import java.math.BigDecimal;
import java.util.Map;

public abstract class Tool {
    private static final Map<String, ToolBrand> brandMap = Map.of(
            "S", ToolBrand.Stihl,
            "W", ToolBrand.Werner,
            "D", ToolBrand.DeWalt,
            "R", ToolBrand.Ridgid
    );
    private final String code;

    Tool(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public ToolBrand getBrand() {
        String brandCode = (code).substring(3);
        return brandMap.get(brandCode);
    }

    public abstract ToolTypeName getToolTypeName();

    public abstract BigDecimal getDailyCharge();

    public abstract Boolean getIsWeekdayCharge();

    public abstract Boolean getIsWeekendCharge();

    public abstract Boolean getIsHolidayCharge();

    @Override
    public String toString() {
        return ("Tool code: " + getCode() + "\n"
                + "Tool type: " + getToolTypeName() + "\n"
                + "Tool brand: " + getBrand());
    }

}
