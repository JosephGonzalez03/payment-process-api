package com.nerd.PaymentProcessApi.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpProperties {
    private String protocol;
    private String host;
    private Integer port;
    private String basePath;
}
