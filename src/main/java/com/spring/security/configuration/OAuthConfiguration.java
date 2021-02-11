package com.spring.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

	@Value("${jwt.clientId}")
	private String clientId;

	@Value("${jwt.client-secret}")
	private String clientSecret;

	@Value("${jwt.signing-key}")
	private String jwtSigningKey;

	@Value("${jwt.accessTokenValidititySeconds}") // 12 hours
	private int accessTokenValiditySeconds;

	@Value("${jwt.authorizedGrantTypes}")
	private String[] authorizedGrantTypes;

	@Value("${jwt.refreshTokenValiditySeconds}") // 30 days
	private int refreshTokenValiditySeconds;

	static final String IMPLICIT = "implicit";
	static final String SCOPE_READ = "read";
	static final String SCOPE_WRITE = "write";
	static final String TRUST = "trust";
	static final String CLIENT_SECRET = "$2y$12$bH6xkSH1PHPgBU2Jb7Gb1eupqhp1Mb/oFeMeOHOmcd6fz9w9a6.Rq";

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		.withClient(clientId).secret(clientSecret)
		.authorizedGrantTypes(authorizedGrantTypes).scopes(SCOPE_READ, SCOPE_WRITE)
		.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "USER","ADMIN")
		.autoApprove(true)
		.accessTokenValiditySeconds(accessTokenValiditySeconds)//Access token is only valid for 3 minutes.
        .refreshTokenValiditySeconds(refreshTokenValiditySeconds);//Refresh token is only valid for 10 minutes.;
	}

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).accessTokenConverter(defaultAccessTokenConverter())
    	.userDetailsService(userDetailsService);
    }

	@Bean
	public TokenStore tokenStore(){
		return new JwtTokenStore(defaultAccessTokenConverter());	
	}

	@Bean
	public JwtAccessTokenConverter defaultAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(jwtSigningKey);
		return converter;
	}
}
