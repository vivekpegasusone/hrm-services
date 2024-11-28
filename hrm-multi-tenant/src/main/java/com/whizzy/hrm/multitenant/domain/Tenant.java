package com.whizzy.hrm.multitenant.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table (name = "tenant_data_source")
public class Tenant implements Serializable {

    @Serial
    private static final long serialVersionUID = 3210275181684883952L;

    @Id
    private long id;

    @NotNull
    @Column(name = "tenant", nullable = false)
    private String name;

    @NotNull
    @Column(name = "module", nullable = false)
    private String domain;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "driver_class_name", nullable = false)
    private String driverClass;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @NotNull
    @Column(name = "maximum_pool_size", nullable = false)
    private Integer maxPoolSize;

    @NotNull
    @Column(name = "minimum_idle", nullable = false)
    private Integer minIdle;

    @NotNull
    @Column(name = "max_lifetime", nullable = false)
    private Integer maxLifeTime;

    @NotNull
    @Column(name = "keep_alive_time", nullable = false)
    private Integer keepAliveTime;

    @NotNull
    @Column(name = "connection_timeout", nullable = false)
    private Integer connectionTimeout;
}
