package com.money.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration 
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;
 	
	
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory().
			withClient("angular").
				secret("$2a$10$uC.oL8K55bAo/xZDqvbIZu4hqRX4Ga5In0cpswKeuMu6.RaMkdGxu").
				scopes("read","write").
				authorizedGrantTypes("password","refresh_token").
				accessTokenValiditySeconds(1800).
				refreshTokenValiditySeconds(3600 *24)
			.and()
			.withClient("mobile").
				secret("$2a$10$nSd7E8SO0In9wshWnQbvWO5faL22O316KDkxFg.XrJq1OoX9HtjQ2").
				scopes("read").
				authorizedGrantTypes("password","refresh_token").
				accessTokenValiditySeconds(1800).
				refreshTokenValiditySeconds(3600 *24);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).
		accessTokenConverter(accessTokenConverter()).
		reuseRefreshTokens(false).
		authenticationManager(authenticationManager);
			
	}
 
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter acessTokernConverter = new JwtAccessTokenConverter();
		acessTokernConverter.setSigningKey("angular");
		return acessTokernConverter;
	}

	@Bean
	public TokenStore tokenStore(){
		return new JwtTokenStore(accessTokenConverter());
	}
}
