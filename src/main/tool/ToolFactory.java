package tool;

public class ToolFactory {
    public Tool getTool(String code) {
        String toolTypeCode = (code).substring(0, 3);

        switch (toolTypeCode) {
            case "CHN":
                return new Chainsaw(code);
            case "LAD":
                return new Ladder(code);
            case "JAK":
                return new Jackhammer(code);
            default:
                return null;
        }
    }
}
