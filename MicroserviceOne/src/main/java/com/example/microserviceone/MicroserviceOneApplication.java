package com.example.microserviceone;

import com.example.microserviceone.domain.Role;
import com.example.microserviceone.domain.User;
import com.example.microserviceone.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroserviceOneApplication {

    public static void main(String[] args) { SpringApplication.run(MicroserviceOneApplication.class, args); }

}
