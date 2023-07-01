package main.tool;

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
}
