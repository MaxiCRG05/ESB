package com.aos.esb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AosEsbApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(AosEsbApplication.class, args);
    }
}