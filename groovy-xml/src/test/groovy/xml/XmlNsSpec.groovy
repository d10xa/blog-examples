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

    def "find all namespaces in xml elements"() {
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

    def 'attributes with namespaces'() {
        given: "xml file with xsi:schemaLocation attribute"
        String xmlString = file('with-attributes.xml').text
        xml = new XmlSlurper().parseText(xmlString)

        when:
        List<String> namespaces = xml.'**'.inject([]) { list, el ->
            list += el.namespaceURI()
            list += el.attributes()*.key
                    .findAll { it.startsWith("{") }
                    .collect { it[1..it.indexOf("}")-1] }
        }.unique().minus('')

        then:
        namespaces.contains "http://www.w3.org/2001/XMLSchema-instance"
    }

    private File file(String path) {
        new File(getClass().getClassLoader().getResource(path).toURI())
    }

}
