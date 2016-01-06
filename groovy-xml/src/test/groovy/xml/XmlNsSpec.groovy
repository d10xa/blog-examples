package xml

import groovy.util.slurpersupport.GPathResult
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
import spock.lang.Specification

class XmlNsSpec extends Specification {

    GPathResult xml

    def setup() {
        String xmlString = file('soap1.xml').text
        xml = new XmlSlurper().parseText(xmlString)
    }

    def "find all namespaces in xml"() {
        when:
        List<String> namespaces = xml.'**'.collect { it.namespaceURI() }.unique()

        then:
        ['http://schemas.xmlsoap.org/soap/envelope/',
         'http://weather',
         'http://help.weather'] == namespaces
    }

    def "declare all namespaces in root element"() {
        given:
        def prettyNs = ['http://schemas.xmlsoap.org/soap/envelope/': 'e',
                        'http://weather'                           : 'w',
                        'http://help.weather'                      : 'h',
                        'http://unused.ns'                         : 'unused']
        when:
        List<String> namespaces = xml.'**'.collect { it.namespaceURI() }.unique()
        String result = XmlUtil.serialize(new StreamingMarkupBuilder().bind {
            prettyNs.findAll { namespaces.contains(it.key) }.each { ns ->
                mkp.declareNamespace((ns.value): ns.key)
            }
            mkp.yield xml
        })

        then:
        result == XmlUtil.serialize(file('soap2.xml').text)
    }

    private File file(String path) {
        new File(getClass().getClassLoader().getResource(path).toURI())
    }

}
