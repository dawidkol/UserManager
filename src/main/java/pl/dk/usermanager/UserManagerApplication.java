package pl.dk.usermanager;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class UserManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagerApplication.class, args);


    }
    @PostConstruct
    void aaa () {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String p1 = passwordEncoder.encode("hardPass");
        System.out.println(p1);

        String p2 = passwordEncoder.encode("securePassword");
        System.out.println(p2);

        String p3 = passwordEncoder.encode("safePass123");
        System.out.println(p3);

        String p4 = passwordEncoder.encode("strongPassword");
        System.out.println(p4);

        String p5 = passwordEncoder.encode("p@ssw0rd!");
        System.out.println(p5);

        String p6 = passwordEncoder.encode("pass123word");
        System.out.println(p6);

        String p7 = passwordEncoder.encode("testPassword");
        System.out.println(p7);

        String p8 = passwordEncoder.encode("myPassWord123");
        System.out.println(p8);

        String p9 = passwordEncoder.encode("JudgementDay21");
        System.out.println(p9);

        String p10 = passwordEncoder.encode("Sp1d3rM@n");
        System.out.println(p10);
    }

}
