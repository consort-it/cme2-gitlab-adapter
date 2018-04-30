package com.consort.security;

import org.pac4j.core.authorization.authorizer.RequireAnyAttributeAuthorizer;

public class GitlabAdapterAttributeAuthorizer extends RequireAnyAttributeAuthorizer {

    public GitlabAdapterAttributeAuthorizer(final String attribute, final String valueToMatch) {
        super(valueToMatch);
        setElements(attribute);
    }
}
