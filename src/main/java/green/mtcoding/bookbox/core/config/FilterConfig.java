package green.mtcoding.bookbox.core.config;
import green.mtcoding.bookbox.core.filter.UrlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<?> urlValidationFilter() {
        FilterRegistrationBean<UrlFilter> urlValidationFilter = new FilterRegistrationBean(new UrlFilter());
        urlValidationFilter.addUrlPatterns("/api/*");
        return urlValidationFilter;
    }

}
