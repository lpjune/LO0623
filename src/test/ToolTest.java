import enums.ToolBrand;
import enums.ToolTypeName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tool.Tool;
import tool.ToolFactory;

import static org.junit.jupiter.api.Assertions.*;

class ToolTest {
    ToolFactory toolFactory = new ToolFactory();

    private final Tool tool1 = toolFactory.getTool("CHNS");;
    private final Tool tool2 = toolFactory.getTool("LADW");
    private final Tool tool3  = toolFactory.getTool("JAKD");
    private final Tool tool4 = toolFactory.getTool("JAKR");

    @Test
    @DisplayName("Should return correct tool code")
    void shouldReturnCorrectToolCode() {
        Assertions.assertAll(() -> assertEquals(tool1.getCode(), "CHNS"),
                () -> assertEquals(tool2.getCode(), "LADW"),
                () -> assertEquals(tool3.getCode(), "JAKD"),
                () -> assertEquals(tool4.getCode(), "JAKR"));
    }

    @Test
    @DisplayName("Should return correct tool brand")
    void shouldReturnCorrectToolBrand() {
        Assertions.assertAll(() -> assertEquals(tool1.getBrand(), ToolBrand.Stihl),
                             () -> assertEquals(tool2.getBrand(), ToolBrand.Werner),
                             () -> assertEquals(tool3.getBrand(), ToolBrand.DeWalt),
                             () -> assertEquals(tool4.getBrand(), ToolBrand.Ridgid));
    }

    @Test
    @DisplayName("Should return correct tool type name")
    void shouldReturnCorrectToolTypeName() {
        Assertions.assertAll(() -> assertEquals(tool1.getToolTypeName(), ToolTypeName.Chainsaw),
                () -> assertEquals(tool2.getToolTypeName(), ToolTypeName.Ladder),
                () -> assertEquals(tool3.getToolTypeName(), ToolTypeName.Jackhammer),
                () -> assertEquals(tool4.getToolTypeName(), ToolTypeName.Jackhammer));
    }
}