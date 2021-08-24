package com.swvalerian.springrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// без этой аннотации была ошибка
// 2021-08-20 15:40:27.054 ERROR 6280 --- [nio-8080-exec-9] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.swvalerian.springrestapi.model.User$HibernateProxy$gJ2D26ZS["hibernateLazyInitializer"])] with root cause
@JsonIgnoreProperties({"hibernateLazyInitializer"})

@Entity
@Data
//@NoArgsConstructor

@Table(name = "files")
public class File extends BaseEntity {

    @Column(name = "location")
    private String location;

    public File() {
    }

    public File(String location) {
        this.location = location;
    }

    //    @OneToOne(mappedBy = "file")
//    private Event event;
}
