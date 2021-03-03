package com.meituan.hotel.dsl.groovy;

import com.google.common.collect.ImmutableMap;
import com.lesofn.runner.GroovyScriptRunner;
import dsl.groovy.exception.DSLException;
import groovy.lang.Script;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author lesofn
 * @version 1.0 2021-03-02 11:59
 */
public class DslParamsTest {
    private static final Map<String, Object> params = ImmutableMap.of();

    @Test
    public void testInt1() throws IOException {
        Script script = GroovyScriptRunner.getScript("params", "checkInt1", params);
        script.run();
    }

    @Test
    public void testInt2() {
        assertThrows(DSLException.class, () -> {
            Script script = GroovyScriptRunner.getScript("params", "checkInt2", params);
            script.run();
        });
    }

    @Test
    public void testLong1() {
        assertThrows(DSLException.class, () -> {
            Script script = GroovyScriptRunner.getScript("params", "checkLong1", params);
            script.run();
        });
    }

    @Test
    public void testLong2() {
        assertThrows(DSLException.class, () -> {
            Script script = GroovyScriptRunner.getScript("params", "checkLong2", params);
            script.run();
        });
    }

    @Test
    public void testStr1() throws IOException {
        Script script = GroovyScriptRunner.getScript("params", "checkStr1", params);
        script.run();
    }

    @Test
    public void testStr2() {
        assertThrows(DSLException.class, () -> {
            Script script = GroovyScriptRunner.getScript("params", "checkStr2", params);
            script.run();
        });
    }

    @Test
    public void testStr3() {
        assertThrows(DSLException.class, () -> {
            Script script = GroovyScriptRunner.getScript("params", "checkStr3", params);
            script.run();
        });
    }
}
