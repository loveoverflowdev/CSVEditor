

package org.asura.csveditor.validation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.asura.csveditor.validation.configuration.ValidationConfiguration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * unit test for header validator
 */
public class HeaderValidationTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // tests
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @ParameterizedTest
    @MethodSource("validationConfigurations")
    public void validation(String configHeaderNames,
                           String[] headerNames,
                           Boolean expectedResult,
                           List<ValidationMessage> expectedErrors) {
        // setup
        Gson gson = new GsonBuilder().create();
        ValidationConfiguration config = gson.fromJson(configHeaderNames, ValidationConfiguration.class);
        Validator sut = new Validator(config, new TestColumnValueProvider());

        // execution
        ValidationError result = sut.isHeaderValid(headerNames);

        // assertion
        assertThat(result == null, is(expectedResult));
        if (!expectedResult) {
            assertTrue(result.getMessages().containsAll(expectedErrors));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // parameters for tests
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Stream<Arguments> validationConfigurations() {
        return Stream.of(
                Arguments.of( json(), new String[] {}, true, null ),
                Arguments.of( json("a"), new String[] {"a"}, true, null ),
                Arguments.of( json("a"), new String[] {"b"}, false, singletonList(new ValidationMessage("validation.message.header.match", "0", "a", "b"))),
                Arguments.of( json("a"), new String[] {"a","b"}, false, singletonList(new ValidationMessage("validation.message.header.length", "2", "1"))),
                Arguments.of( json("a", "b"), new String[] {"b", "a"}, false, asList(new ValidationMessage("validation.message.header.match", "0", "a", "b"), new ValidationMessage("validation.message.header.match", "1", "b", "a")) )
        );
    }

    @SuppressWarnings("StringConcatenationInLoop")
    public static String json(String... headerNames) {

        String json = "{ \"fields\": [";

        for (String headerName: headerNames) {
            json += "{\"name\" : \""+headerName+"\" },";
        }

        if (headerNames.length > 0) {
            json = json.substring(0, json.length() - 1);
        }

        json += "]}";


        return  json;
    }

}
