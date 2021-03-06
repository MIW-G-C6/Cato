package nl.miwgroningen.se6.heartcoded.CaTo.configuration;

import nl.miwgroningen.se6.heartcoded.CaTo.service.CatoUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Determine what security should go where.
 */

@Configuration
@EnableWebSecurity
public class CatoSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private CatoUserDetailService catoUserDetailService;

    public CatoSecurityConfiguration(CatoUserDetailService catoUserDetailService) {
        super();
        this.catoUserDetailService = catoUserDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/siteAdmin/dashboard").hasAuthority("ROLE_ADMIN")
                .antMatchers("/users/delete/*").hasAuthority("ROLE_ADMIN")
                .antMatchers("/css/**", "/webjars/**", "/javascript/**").permitAll()
                .antMatchers("/","/registration").permitAll()
                .antMatchers("/about", "/contact").permitAll()
                .anyRequest().authenticated().and()
                .formLogin()
                .loginPage("/")
                .defaultSuccessUrl("/loginRedirect", true)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(catoUserDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
