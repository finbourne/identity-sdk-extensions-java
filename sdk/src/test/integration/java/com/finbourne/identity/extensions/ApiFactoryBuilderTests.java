package com.finbourne.identity.extensions;

import com.finbourne.identity.ApiException;
import com.finbourne.identity.api.RolesApi;
import com.finbourne.identity.model.RoleResponse;
import com.finbourne.identity.extensions.auth.FinbourneTokenException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

public class ApiFactoryBuilderTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void build_WithExistingConfigurationFile_ShouldReturnFactory() throws ApiException, ApiConfigurationException, FinbourneTokenException {
        ApiFactory apiFactory = ApiFactoryBuilder.build(CredentialsSource.credentialsFile);
        assertThat(apiFactory, is(notNullValue()));
        assertThatFactoryBuiltApiCanMakeLUSIDCalls(apiFactory);
    }

    private static void assertThatFactoryBuiltApiCanMakeLUSIDCalls(ApiFactory apiFactory) throws ApiException {
        RolesApi rolesApi = apiFactory.build(RolesApi.class);
        List<RoleResponse> roleResponses = rolesApi.listRoles();
        assertThat("Roles API created by factory should return list of roles"
                , roleResponses, is(notNullValue()));
        assertThat("List of roles should not be empty",
                roleResponses, not(empty()));
    }

}
