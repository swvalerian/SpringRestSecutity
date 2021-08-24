package com.swvalerian.springrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

// без этой аннотации была ошибка
// 2021-08-20 15:40:27.054 ERROR 6280 --- [nio-8080-exec-9] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.swvalerian.springrestapi.model.User$HibernateProxy$gJ2D26ZS["hibernateLazyInitializer"])] with root cause
@JsonIgnoreProperties({"hibernateLazyInitializer"})

@Entity
// @Data
@Getter
@Setter
@ToString

//@NoArgsConstructor

@Table(name = "events")
public class Event extends BaseEntity{
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "updated")
    private LocalDateTime updated;
    @Column(name = "deleted")
    private LocalDateTime deleted;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    public Event() {
    }

    public Event(LocalDateTime created, LocalDateTime updated, LocalDateTime deleted, File file, User user) {
        this.created = created;
        this.updated = updated;
        this.deleted = deleted;
        this.file = file;
        this.user = user;
    }
}
