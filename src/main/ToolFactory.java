
import enums.ToolTypeName;

import java.math.BigDecimal;

public class ToolFactory {
    public Tool getTool(String code) {
        String toolTypeCode = (code).substring(0, 3);


        if (toolTypeCode.equals("CHN")) {
            return new Chainsaw(code);
        } else if (toolTypeCode.equals("LAD")) {
            return new Ladder(code);
        } else if (toolTypeCode.equals("JAK")) {
            return new Jackhammer(code);
        } else {
            return null;
        }
    }

    private static class Ladder extends Tool {

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

    private static class Chainsaw extends Tool {

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

    private static class Jackhammer extends Tool {

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
}
