package pl.dk.usermanager.domain.confirmationToken;

import lombok.Getter;
import pl.dk.usermanager.domain.user.User;
import pl.dk.usermanager.domain.user.UserFacade;
import pl.dk.usermanager.domain.user.UserFacadeTestData;

import java.util.List;

@Getter
public class TokenTestData {

    UserFacadeTestData testData = new UserFacadeTestData();
    private List<ConfirmationToken> tokens = List.of(
            new ConfirmationToken(1L, "8b4cb114-0296-4bc5-b370-2e0f5d423a09",
                    testData.getUsers().get(0))
    );

}
