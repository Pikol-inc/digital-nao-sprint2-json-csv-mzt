import com.pikolinc.Config.java;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * JUnit test suite for the Config class.
 *
 * <p>This test class provides comprehensive coverage of the Config class functionality,
 * including default configuration values, CLI argument parsing, delimiter handling,
 * file path configuration, and edge cases.</p>
 *
 * <p>The test suite uses stream redirection to capture and verify console output
 * messages, ensuring that user feedback is properly displayed.</p>
 *
 * @author Generated Test Suite
 * @version 1.0
 * @see Config
 */
public class ConfigTest {

    /** Stream to capture System.out output during tests. */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /** Stream to capture System.err output during tests. */
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    /** Original System.out stream to restore after tests. */
    private final PrintStream originalOut = System.out;

    /** Original System.err stream to restore after tests. */
    private final PrintStream originalErr = System.err;

    /**
     * Sets up stream redirection before each test.
     *
     * <p>Redirects System.out and System.err to ByteArrayOutputStream instances
     * so that console output can be captured and verified during testing.</p>
     */
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * Restores original streams after each test.
     *
     * <p>Restores System.out and System.err to their original states
     * to prevent interference between tests.</p>
     */
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    /**
     * Tests that a Config instance has the correct default values.
     *
     * <p>Verifies that when creating a Config object using the default constructor,
     * all fields are initialized with the expected default values for input files,
     * output files, and delimiter.</p>
     */
    @Test
    public void testDefaultConfiguration() {
        Config config = new Config();

        assertEquals("researcher_publications.json", config.inputResearchers);
        assertEquals("department_statistics.json", config.inputDepartments);
        assertEquals("journal_impact_data.json", config.inputJournals);
        assertEquals("scientific_publications_report.csv", config.outputResearchers);
        assertEquals("department_summary_report.csv", config.outputDepartments);
        assertEquals("journal_impact_analysis.csv", config.outputJournals);
        assertEquals(',', config.delimiter);
    }

    /**
     * Tests Config.fromArgs() with an empty arguments array.
     *
     * <p>Verifies that when no command line arguments are provided,
     * the method returns a Config object with default values and
     * displays the appropriate default delimiter message.</p>
     */
    @Test
    public void testFromArgsWithEmptyArray() {
        String[] args = {};
        Config config = Config.fromArgs(args);

        // Should return default config
        assertEquals("researcher_publications.json", config.inputResearchers);
        assertEquals(',', config.delimiter);
        assertTrue(outContent.toString().contains("Using default delimiter"));
    }

    /**
     * Tests delimiter parsing with semicolon.
     *
     * <p>Verifies that the --delimiter=; argument correctly sets
     * the delimiter field to a semicolon character.</p>
     */
    @Test
    public void testFromArgsWithDelimiterSemicolon() {
        String[] args = {"--delimiter=;"};
        Config config = Config.fromArgs(args);

        assertEquals(';', config.delimiter);
    }

    /**
     * Tests delimiter parsing with tab using escape sequence.
     *
     * <p>Verifies that the --delimiter=\\t argument correctly sets
     * the delimiter field to a tab character.</p>
     */
    @Test
    public void testFromArgsWithDelimiterTab() {
        String[] args = {"--delimiter=\\t"};
        Config config = Config.fromArgs(args);

        assertEquals('\t', config.delimiter);
    }

    /**
     * Tests delimiter parsing with "tab" keyword.
     *
     * <p>Verifies that the --delimiter=tab argument correctly sets
     * the delimiter field to a tab character using the keyword.</p>
     */
    @Test
    public void testFromArgsWithDelimiterTabKeyword() {
        String[] args = {"--delimiter=tab"};
        Config config = Config.fromArgs(args);

        assertEquals('\t', config.delimiter);
    }

    /**
     * Tests delimiter parsing with case-insensitive "TAB" keyword.
     *
     * <p>Verifies that the --delimiter=TAB argument correctly sets
     * the delimiter field to a tab character, demonstrating case insensitivity.</p>
     */
    @Test
    public void testFromArgsWithDelimiterTabCaseInsensitive() {
        String[] args = {"--delimiter=TAB"};
        Config config = Config.fromArgs(args);

        assertEquals('\t', config.delimiter);
    }

    /**
     * Tests delimiter parsing with "space" keyword.
     *
     * <p>Verifies that the --delimiter=space argument correctly sets
     * the delimiter field to a space character using the keyword.</p>
     */
    @Test
    public void testFromArgsWithDelimiterSpace() {
        String[] args = {"--delimiter=space"};
        Config config = Config.fromArgs(args);

        assertEquals(' ', config.delimiter);
    }

    /**
     * Tests delimiter parsing with case-insensitive "SPACE" keyword.
     *
     * <p>Verifies that the --delimiter=SPACE argument correctly sets
     * the delimiter field to a space character, demonstrating case insensitivity.</p>
     */
    @Test
    public void testFromArgsWithDelimiterSpaceCaseInsensitive() {
        String[] args = {"--delimiter=SPACE"};
        Config config = Config.fromArgs(args);

        assertEquals(' ', config.delimiter);
    }

    /**
     * Tests delimiter parsing with pipe character.
     *
     * <p>Verifies that the --delimiter=| argument correctly sets
     * the delimiter field to a pipe character.</p>
     */
    @Test
    public void testFromArgsWithDelimiterPipe() {
        String[] args = {"--delimiter=|"};
        Config config = Config.fromArgs(args);

        assertEquals('|', config.delimiter);
    }

    /**
     * Tests delimiter parsing with multiple character string.
     *
     * <p>Verifies that when a multi-character delimiter is provided,
     * the parser uses only the first character and displays a warning message.</p>
     */
    @Test
    public void testFromArgsWithMultipleCharacterDelimiter() {
        String[] args = {"--delimiter=abc"};
        Config config = Config.fromArgs(args);

        assertEquals('a', config.delimiter);
        assertTrue(errContent.toString().contains("Warning: Delimiter must be a single character"));
        assertTrue(errContent.toString().contains("Using first character: 'a'"));
    }

    /**
     * Tests delimiter parsing with empty delimiter value.
     *
     * <p>Verifies that when an empty delimiter is provided (--delimiter=),
     * the parser falls back to the default comma delimiter.</p>
     */
    @Test
    public void testFromArgsWithEmptyDelimiter() {
        String[] args = {"--delimiter="};
        Config config = Config.fromArgs(args);

        assertEquals(',', config.delimiter); // Should fallback to default
    }

    /**
     * Tests input researchers file path configuration.
     *
     * <p>Verifies that the --input-researchers argument correctly sets
     * the inputResearchers field to the specified file path.</p>
     */
    @Test
    public void testFromArgsWithInputResearchers() {
        String[] args = {"--input-researchers=custom_researchers.json"};
        Config config = Config.fromArgs(args);

        assertEquals("custom_researchers.json", config.inputResearchers);
    }

    /**
     * Tests input departments file path configuration.
     *
     * <p>Verifies that the --input-departments argument correctly sets
     * the inputDepartments field to the specified file path.</p>
     */
    @Test
    public void testFromArgsWithInputDepartments() {
        String[] args = {"--input-departments=custom_departments.json"};
        Config config = Config.fromArgs(args);

        assertEquals("custom_departments.json", config.inputDepartments);
    }

    /**
     * Tests input journals file path configuration.
     *
     * <p>Verifies that the --input-journals argument correctly sets
     * the inputJournals field to the specified file path.</p>
     */
    @Test
    public void testFromArgsWithInputJournals() {
        String[] args = {"--input-journals=custom_journals.json"};
        Config config = Config.fromArgs(args);

        assertEquals("custom_journals.json", config.inputJournals);
    }

    /**
     * Tests output researchers file path configuration.
     *
     * <p>Verifies that the --output-researchers argument correctly sets
     * the outputResearchers field to the specified file path.</p>
     */
    @Test
    public void testFromArgsWithOutputResearchers() {
        String[] args = {"--output-researchers=custom_output.csv"};
        Config config = Config.fromArgs(args);

        assertEquals("custom_output.csv", config.outputResearchers);
    }

    /**
     * Tests output departments file path configuration.
     *
     * <p>Verifies that the --output-departments argument correctly sets
     * the outputDepartments field to the specified file path.</p>
     */
    @Test
    public void testFromArgsWithOutputDepartments() {
        String[] args = {"--output-departments=custom_dept_output.csv"};
        Config config = Config.fromArgs(args);

        assertEquals("custom_dept_output.csv", config.outputDepartments);
    }

    /**
     * Tests output journals file path configuration.
     *
     * <p>Verifies that the --output-journals argument correctly sets
     * the outputJournals field to the specified file path.</p>
     */
    @Test
    public void testFromArgsWithOutputJournals() {
        String[] args = {"--output-journals=custom_journal_output.csv"};
        Config config = Config.fromArgs(args);

        assertEquals("custom_journal_output.csv", config.outputJournals);
    }

    /**
     * Tests parsing multiple command line arguments together.
     *
     * <p>Verifies that when multiple arguments are provided simultaneously,
     * each is correctly parsed and applied while unspecified fields retain
     * their default values.</p>
     */
    @Test
    public void testFromArgsWithMultipleArguments() {
        String[] args = {
                "--delimiter=|",
                "--input-researchers=test_researchers.json",
                "--output-departments=test_output.csv"
        };
        Config config = Config.fromArgs(args);

        assertEquals('|', config.delimiter);
        assertEquals("test_researchers.json", config.inputResearchers);
        assertEquals("test_output.csv", config.outputDepartments);
        // Other values should remain default
        assertEquals("department_statistics.json", config.inputDepartments);
    }

    /**
     * Tests parsing all possible command line arguments.
     *
     * <p>Verifies that when all supported arguments are provided,
     * each field in the Config object is correctly set to the
     * corresponding argument value.</p>
     */
    @Test
    public void testFromArgsWithAllArguments() {
        String[] args = {
                "--delimiter=;",
                "--input-researchers=r.json",
                "--input-departments=d.json",
                "--input-journals=j.json",
                "--output-researchers=r.csv",
                "--output-departments=d.csv",
                "--output-journals=j.csv"
        };
        Config config = Config.fromArgs(args);

        assertEquals(';', config.delimiter);
        assertEquals("r.json", config.inputResearchers);
        assertEquals("d.json", config.inputDepartments);
        assertEquals("j.json", config.inputJournals);
        assertEquals("r.csv", config.outputResearchers);
        assertEquals("d.csv", config.outputDepartments);
        assertEquals("j.csv", config.outputJournals);
    }

    /**
     * Tests behavior with unrecognized command line arguments.
     *
     * <p>Verifies that when unrecognized arguments are mixed with
     * valid arguments, the valid arguments are processed correctly
     * and unrecognized arguments are silently ignored.</p>
     */
    @Test
    public void testFromArgsWithUnrecognizedArguments() {
        String[] args = {
                "--delimiter=;",
                "--unknown-argument=value",
                "--input-researchers=test.json"
        };
        Config config = Config.fromArgs(args);

        assertEquals(';', config.delimiter);
        assertEquals("test.json", config.inputResearchers);
        // Unknown arguments should be ignored
        assertEquals("department_statistics.json", config.inputDepartments);
    }

    /**
     * Tests behavior with empty argument values.
     *
     * <p>Verifies that when arguments are provided with empty values
     * (e.g., --input-researchers=), the corresponding fields are
     * set to empty strings rather than null or default values.</p>
     */
    @Test
    public void testFromArgsWithEmptyValues() {
        String[] args = {
                "--input-researchers=",
                "--delimiter=;",
                "--output-journals="
        };
        Config config = Config.fromArgs(args);

        assertEquals("", config.inputResearchers);
        assertEquals("", config.outputJournals);
        assertEquals(';', config.delimiter);
    }

    /**
     * Tests parsing file paths containing spaces.
     *
     * <p>Verifies that file paths with spaces are correctly parsed
     * and assigned to the appropriate Config fields without truncation
     * or modification.</p>
     */
    @Test
    public void testFromArgsWithPathsContainingSpaces() {
        String[] args = {
                "--input-researchers=path with spaces/file.json",
                "--output-departments=output path/file.csv"
        };
        Config config = Config.fromArgs(args);

        assertEquals("path with spaces/file.json", config.inputResearchers);
        assertEquals("output path/file.csv", config.outputDepartments);
    }

    /**
     * Tests parsing file paths with special characters.
     *
     * <p>Verifies that file paths containing special characters
     * (hyphens, underscores, dots, @, #, $, %) are correctly
     * parsed and preserved in the Config fields.</p>
     */
    @Test
    public void testFromArgsWithSpecialCharactersInPaths() {
        String[] args = {
                "--input-researchers=data/file-name_with.special@chars.json",
                "--output-researchers=out/file#with$special%chars.csv"
        };
        Config config = Config.fromArgs(args);

        assertEquals("data/file-name_with.special@chars.json", config.inputResearchers);
        assertEquals("out/file#with$special%chars.csv", config.outputResearchers);
    }

    /**
     * Tests that argument order doesn't affect parsing results.
     *
     * <p>Verifies that the same arguments provided in different orders
     * produce identical Config objects, demonstrating order independence.</p>
     */
    @Test
    public void testFromArgsArgumentOrder() {
        // Test that argument order doesn't matter
        String[] args1 = {"--delimiter=|", "--input-researchers=test.json"};
        String[] args2 = {"--input-researchers=test.json", "--delimiter=|"};

        Config config1 = Config.fromArgs(args1);
        Config config2 = Config.fromArgs(args2);

        assertEquals(config1.delimiter, config2.delimiter);
        assertEquals(config1.inputResearchers, config2.inputResearchers);
    }

    /**
     * Tests that default delimiter message is displayed when appropriate.
     *
     * <p>Verifies that when no delimiter is explicitly specified,
     * the system displays an informative message about using the
     * default comma delimiter.</p>
     */
    @Test
    public void testDefaultDelimiterMessage() {
        String[] args = {"--input-researchers=test.json"}; // No delimiter specified
        Config config = Config.fromArgs(args);

        assertEquals(',', config.delimiter);
        assertTrue(outContent.toString().contains("Using default delimiter: ',' (comma)"));
    }

    /**
     * Tests that default delimiter message is not shown when delimiter is set.
     *
     * <p>Verifies that when a delimiter is explicitly specified,
     * the default delimiter message is not displayed to the user.</p>
     */
    @Test
    public void testNoDefaultDelimiterMessageWhenExplicitlySet() {
        String[] args = {"--delimiter=;"};
        Config config = Config.fromArgs(args);

        assertEquals(';', config.delimiter);
        assertFalse(outContent.toString().contains("Using default delimiter"));
    }

    /**
     * Tests that the Config class can be instantiated successfully.
     *
     * <p>A basic sanity check to ensure that the Config class
     * exists and can be properly instantiated without errors.</p>
     *
     * <p><strong>Note:</strong> Testing --help and -h flags is not included
     * in this test suite because they call System.exit(0), which would
     * terminate the test runner. To test those scenarios, a mocking
     * framework like PowerMock would be required to intercept the
     * System.exit() call.</p>
     */
    @Test
    public void testConfigClassExists() {
        // Verify the Config class can be instantiated
        Config config = new Config();
        assertNotNull(config);
    }
}