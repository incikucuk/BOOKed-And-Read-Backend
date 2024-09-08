package com.ikucuk.springbootlibrary.config;

import com.ikucuk.springbootlibrary.entity.Book;
import com.ikucuk.springbootlibrary.entity.Review;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private String theAllowedOrigins = "http://localhost:3000";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors){
        HttpMethod[] theUnsupportedActions = {    //atılan her istekte kullanıcıya gösterilmesi istenmeyen durumları korumak icin yapilir
                HttpMethod.POST,
                HttpMethod.PATCH,
                HttpMethod.DELETE,
                HttpMethod.PUT};
        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(Review.class);

        disableHttpMethods(Book.class,config,theUnsupportedActions);
        disableHttpMethods(Review.class,config,theUnsupportedActions);

        /* Configure CORS Mapping */
        cors.addMapping(config.getBasePath() + "/**")
                .allowedOrigins(theAllowedOrigins).allowedHeaders("*");

    }

    private void disableHttpMethods(Class theClass,
                                    RepositoryRestConfiguration config,
                                    HttpMethod[] theUnsupportedActions){
                config.getExposureConfiguration().
                forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) ->
                        httpMethods.disable(theUnsupportedActions)).
                        withCollectionExposure(((metdata, httpMethods) ->
                                httpMethods.disable(theUnsupportedActions)));
    }
}
