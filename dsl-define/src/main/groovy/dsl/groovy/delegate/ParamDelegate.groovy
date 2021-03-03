package dsl.groovy.delegate


import dsl.groovy.exception.DSLException
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils

/**
 * 参数处理方法
 * @author lesofn
 * @version 1.0 2021-03-02 11:59
 */
trait ParamDelegate {
    int intParam(String str) {
        return intParam(str, Collections.emptyMap())
    }

    int intParam(String str, Map<String, Object> checks) {
        Object min = checks.get("min")
        Object max = checks.get("max")
        int res = NumberUtils.toInt(StringUtils.strip(str))
        if (min != null && min instanceof Number && res < ((Number) min).intValue()) {
            throw new DSLException("param should < " + min + ", but got: " + res)
        }
        if (max != null && max instanceof Number && res > ((Number) max).intValue()) {
            throw new DSLException("param should > " + max + ", but got: " + res)
        }
        return res
    }

    long longParam(String str) {
        return longParam(str, Collections.emptyMap())
    }

    long longParam(String str, Map<String, Object> checks) {
        Object min = checks.get("min")
        Object max = checks.get("max")
        int res = NumberUtils.toInt(StringUtils.strip(str))
        if (min != null && min instanceof Number && res < ((Number) min).longValue()) {
            throw new DSLException("param should < " + min + ", but got: " + res)
        }
        if (max != null && max instanceof Number && res > ((Number) max).longValue()) {
            throw new DSLException("param should > " + max + ", but got: " + res)
        }
        return res
    }

    String strParam(String str, Map<String, Object> checks) {
        Object length = checks.get("length")
        Object minLength = checks.get("minLength")
        Object maxLength = checks.get("maxLength")
        Object regexValue = checks.get("regex")
        Object nullAble = checks.get("nullAble")

        if (nullAble != null && nullAble instanceof Boolean && (Boolean) nullAble && StringUtils.isBlank(str)) {
            throw new DSLException("param should not be null")
        }
        int realLength = StringUtils.length(str)
        if (length != null && length instanceof Integer && ((int) length) > 0 && realLength != (int) length) {
            throw new DSLException("param " + str + " length should = " + length)
        }
        if (minLength != null && minLength instanceof Integer && ((int) minLength) > 0 && realLength < (int) minLength) {
            throw new DSLException("param " + str + " length should >= " + minLength + ", but got: " + realLength)
        }
        if (maxLength != null && maxLength instanceof Integer && ((int) maxLength) > 0 && realLength != (int) maxLength) {
            throw new DSLException("param " + str + " length should <= " + maxLength + ", but got: " + realLength)
        }
        if (regexValue != null && regexValue instanceof String && StringUtils.isNoneBlank((String) regexValue) && !str.matches((String) regexValue)) {
            throw new DSLException("param " + str + " should match: " + regexValue)
        }
        return str
    }

}
