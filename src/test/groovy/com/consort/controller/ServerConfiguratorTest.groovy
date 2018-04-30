package com.consort.controller

import spock.lang.Specification
import org.junit.Test;

class ServerConfiguratorTest extends Specification {

    ServerConfigurator underTest

    @Test
    def "test getting the composed server url"() {

        given:
        String server = "metadata-service"
        String projectId = "123"
        String pathToFile = "path"
        boolean rawFile = true
        String commitId= "123"
        underTest = Spy(ServerConfigurator)

        when:
        def serverConfigurationDefault = underTest.getServerConfigurationDefault(server, projectId)
        def serverConfigurationForFiles = underTest.getServerConfigurationForFiles(server, projectId, pathToFile, rawFile)
        def commitMetadata = underTest.getCommitMetadata(server, projectId, commitId)

        then:
        def urlDefault = new URL(serverConfigurationDefault)
        def urlForFiles = new URL(serverConfigurationForFiles)
        def urlMetadata = new URL(commitMetadata)
    }
}
