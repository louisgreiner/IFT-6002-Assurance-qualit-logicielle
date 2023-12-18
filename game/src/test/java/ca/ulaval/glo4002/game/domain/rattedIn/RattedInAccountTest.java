package ca.ulaval.glo4002.game.domain.rattedIn;

import ca.ulaval.glo4002.game.domain.character.CharacterFactory;
import ca.ulaval.glo4002.game.domain.character.Chinchilla;
import ca.ulaval.glo4002.game.domain.character.RattedInUser;
import ca.ulaval.glo4002.game.domain.character.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RattedInAccountTest {
    private static final String OWNER_NAME = "NAME_TEST";
    private static final String CONTACT_NAME = "ContactName";
    private static final Type CHARACTER_TYPE = Type.CHINCHILLA;
    private static final int SALARY = 100;
    private static final RattedInStatus RATTEDIN_STATUS = RattedInStatus.OPEN_TO_WORK;

    private RattedInUser contactCharacter;
    private RattedInAccount rattedInAccount;

    @BeforeEach
    void createRattedIn() {
        rattedInAccount = new RattedInAccount(OWNER_NAME, RATTEDIN_STATUS);
    }

    @BeforeEach
    void initContactCharacter() {
        CharacterFactory characterFactory = new CharacterFactory();
        contactCharacter = (Chinchilla) characterFactory.create(CONTACT_NAME, CHARACTER_TYPE, SALARY);
    }

    @Test
    void whenIsInitialized_thenContactsIsEmpty() {
        assertTrue(rattedInAccount.isContactsEmpty());
    }

    @Test
    void givenAContactAdded_whenClearContact_thenContactsIsClear() {
        rattedInAccount.addContact(contactCharacter);

        rattedInAccount.clearContacts();

        assertTrue(rattedInAccount.isContactsEmpty());
    }

    @Test
    void whenAContactAdded_thenContactsUpdated() {
        rattedInAccount.addContact(contactCharacter);

        assertTrue(rattedInAccount.isInContacts(contactCharacter));
    }

    @Test
    void whenAContactRemoved_thenContactsUpdated() {
        rattedInAccount.addContact(contactCharacter);

        rattedInAccount.removeContact(contactCharacter);

        assertFalse(rattedInAccount.isInContacts(contactCharacter));
    }

    @Test
    void whenContactAlreadyInContacts_thenContactNotAdded() {
        rattedInAccount.addContact(contactCharacter);

        rattedInAccount.addContact(contactCharacter);
        rattedInAccount.removeContact(contactCharacter);

        assertFalse(rattedInAccount.isInContacts(contactCharacter));
    }

    @Test
    void givenCharacterInContacts_whenRemoveCharacterFromContacts_thenCallRemoveRattedInContact() {
        RattedInUser rattedInUserMock = mock(RattedInUser.class);
        rattedInAccount.addContact(rattedInUserMock);

        rattedInAccount.removeCharacterFromContacts(contactCharacter);

        verify(rattedInUserMock).removeRattedInContact(contactCharacter);
    }
}