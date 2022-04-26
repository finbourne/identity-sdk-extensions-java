package com.finbourne.identity.extensions;

import com.finbourne.identity.ApiClient;
import com.finbourne.identity.api.RolesApi;
import com.finbourne.identity.api.ApplicationsApi;
import com.finbourne.identity.api.UsersApi;
import com.finbourne.identity.model.RoleResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ApiFactoryTest {

    private ApiFactory apiFactory;
    private ApiClient apiClient;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        apiClient = mock(ApiClient.class);
        apiFactory = new ApiFactory(apiClient);
    }

    // General Cases
    @Test
    public void build_ForRolesApi_ReturnRolesApi(){
        RolesApi rolesApi = apiFactory.build(RolesApi.class);
        assertThat(rolesApi, instanceOf(RolesApi.class));
    }

    @Test
    public void build_ForApplicationsApi_ReturnApplicationsApi(){
        ApplicationsApi applicationsApi = apiFactory.build(ApplicationsApi.class);
        assertThat(applicationsApi, instanceOf(ApplicationsApi.class));
    }

    @Test
    public void build_ForUsersApi_ReturnUsersApi(){
        UsersApi usersApi = apiFactory.build(UsersApi.class);
        assertThat(usersApi, instanceOf(UsersApi.class));
    }

    @Test
    public void build_ForAnyApi_SetsTheApiFactoryClientAndNotTheDefault(){
        RolesApi rolesApi = apiFactory.build(RolesApi.class);
        assertThat(rolesApi.getApiClient(), equalTo(apiClient));
    }

    // Singleton Check Cases

    @Test
    public void build_ForSameApiBuiltAgainWithSameFactory_ReturnTheSameSingletonInstanceOfApi(){
        RolesApi rolesApi = apiFactory.build(RolesApi.class);
        RolesApi rolesApiSecond = apiFactory.build(RolesApi.class);
        assertThat(rolesApi, sameInstance(rolesApiSecond));
    }

    @Test
    public void build_ForSameApiBuiltWithDifferentFactories_ReturnAUniqueInstanceOfApi(){
        RolesApi api = apiFactory.build(RolesApi.class);
        RolesApi rolesApiSecond = new ApiFactory(mock(ApiClient.class)).build(RolesApi.class);
        assertThat(api, not(sameInstance(rolesApiSecond)));
    }

    // Error Cases

    @Test
    public void build_ForNonApiPackageClass_ShouldThrowException(){
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("com.finbourne.identity.model.RoleResponse class is not a supported API class. " +
                "Supported API classes live in the " + ApiFactory.API_PACKAGE + " package.");
        apiFactory.build(RoleResponse.class);
    }



}
