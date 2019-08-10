package nl.kabisa.meetup.jwtbased.interceptors;

import nl.kabisa.meetup.jwtbased.interceptors.authentication.AuthenticationInterceptor;
import nl.kabisa.meetup.jwtbased.interceptors.csrf.CsrfInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {

    @Value("${csrf.enable_check}")
    private boolean enableCsrfCheck;

    @Autowired
    private CsrfInterceptor csrfInterceptor;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (enableCsrfCheck) {
            registry.addInterceptor(csrfInterceptor).excludePathPatterns("/error");
        }
        registry.addInterceptor(authenticationInterceptor).excludePathPatterns("/error");
    }
}