package dsl.groovy.delegate

import dsl.groovy.conf.HttpConf
import org.codehaus.groovy.runtime.DefaultGroovyMethods

/**
 * @author lesofn
 * @version 1.0 2021-03-02 11:59
 */
trait HttpDelegate {
    /**
     * http相关
     */
    String http(@DelegatesTo(HttpConf.class) Closure<HttpConf> closure) throws IOException {
        HttpConf conf = new HttpConf()
        DefaultGroovyMethods.with(conf, closure)
        return "mocked http result"
    }

}