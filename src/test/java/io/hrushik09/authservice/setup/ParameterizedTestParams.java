package io.hrushik09.authservice.setup;

import java.util.stream.Stream;

public class ParameterizedTestParams {
    public static Stream<String> blankStrings() {
        return Stream.of(null, "", "   ");
    }
}
