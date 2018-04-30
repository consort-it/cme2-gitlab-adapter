package com.consort.util

import org.junit.Assert
import spock.lang.Specification
import org.junit.Test;

class GitlabNameIdTranslatorTest extends Specification {

    GitlabNameIdTranslator underTest

    @Test
    def "test getting project id for projectname metadata-service"() {

        given:
        String serviceName = "metadata-service"
        String server = "someserver.here"
        String token = "someSpeciAlToKEN"
        underTest = Spy(GitlabNameIdTranslator, constructorArgs: [server, token])

        when:
        def projectId = underTest.translateNameToId(serviceName)

        then:
        underTest.setProtocol() >> {}
        underTest.getProjectId(_ as String) >> { "333" }

        and:
        projectId.equals("333") == true
    }
}
