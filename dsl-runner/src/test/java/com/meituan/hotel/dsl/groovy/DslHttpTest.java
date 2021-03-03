package com.meituan.hotel.dsl.groovy;

import com.google.common.collect.ImmutableMap;
import com.lesofn.runner.GroovyScriptRunner;
import groovy.lang.Script;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author lesofn
 * @version 1.0 2021-03-02 11:59
 */
public class DslHttpTest {
    private static final Map<String, Object> params = ImmutableMap.of();

    @Test
    public void testHttp() throws IOException {
        Script script = GroovyScriptRunner.getScript("http", "http_test", params);
        assertEquals("mocked http result", script.run());
    }

}
