package pl.dk.usermanager.domain.user;

import lombok.Getter;

import java.util.List;


@Getter
class UserFacadeTestData {

    List<User> users = List.of(
            new User(1L, "johndoe@gmail.com", "$2a$10$jQePFQXaRqPJUIK1.p/SOORRvkLdVzSJoVLzcZWbrt0Wd3a/6H/.."),//hardPass
            new User(2L, "janedoe@hotmail.com", "$2a$10$65I0isY3o4L77K130743SuMdgkbRh9lJvUl4R/MDZBd0IvsVsUKsi"),//securePassword
            new User(3L, "alice_smith@yahoo.com", "$2a$10$LQFOhDrfNJlpTEnhJpODA.5KDq24NEVtdBdLPvZ8w/muwT0CKBEs."),//safePass123
            new User(4L, "bob.brown@example.com", "$2a$10$.ekGMhftiiJAefz/1fryR.1yT8HuSXKg4Ym.aGBchfu.2RC6zHNhm"),//strongPassword
            new User(5L, "emily_jones@domain.com", "$2a$10$AmOpU6UdhMmsFRHtpRywsOTJxGWBBCInYxiJn5kcM8fszuw1N4Aa6"),//p@ssw0rd!
            new User(6L, "robert.davis@testmail.com", "$2a$10$B2ZqVbXuoMfYsGT61Exs/.FN2GBLkmIp6859c9BBMzMc.kOUKfrDi"),//pass123word
            new User(7L, "laura_lee@workmail.com", "$2a$10$3e6WifGBv7HcrwnDe4qZO.grpI9mbGoHLkN1lfIlnPYsRDBXPWIXe"),//testPassword
            new User(8L, "mike.smith@myemail.com", "$2a$10$rY6vC4NUP2P4FDp6SIevNOObOZ2ktV27Rb/vPcsMdGsnfZtMD5GVy"),//myPassWord123
            new User(9L, "sarahconnor@skynet.com", "$2a$10$kDOBgehJXEjdd6cXGFpqPuQDKkl0QtGPYLNXadVlF6d3zs3xyj6Hm"),//JudgementDay21
            new User(10L, "peter_parker@dailybugle.com", "$2a$10$Q1IyEh1oTXeUCSWky/LcRuCvgcKtTyg3rHw5NQgzgeVjpPM6u55MC")//Sp1d3rM@n
    );

}
