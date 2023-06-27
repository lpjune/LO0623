import enums.ToolBrand;
import enums.ToolTypeName;

import java.math.BigDecimal;
import java.util.Map;

public abstract class Tool {
    String code;

    Tool(String code) {
        this.code = code;
    }

    private static final Map<String, ToolBrand> brandMap = Map.of(
            "S", ToolBrand.Stihl,
            "W", ToolBrand.Werner,
            "D", ToolBrand.DeWalt,
            "R", ToolBrand.Ridgid
    );

    String getCode() {
        return this.code;
    }

    ToolBrand getBrand() {
        String brandCode = (code).substring(3);
        return brandMap.get(brandCode);
    };
    abstract ToolTypeName getToolTypeName();
    abstract BigDecimal getDailyCharge();
    abstract Boolean getIsWeekdayCharge();
    abstract Boolean getIsWeekendCharge();
    abstract Boolean getIsHolidayCharge();
}
