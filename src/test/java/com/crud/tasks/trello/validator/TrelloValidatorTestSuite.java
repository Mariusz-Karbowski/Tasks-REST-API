package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TrelloValidatorTestSuite {

    @Mock
    TrelloValidator trelloValidator;

    @Test
    void testValidateBoard() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        List<TrelloBoard> trelloBoards = List.of(new TrelloBoard("1", "BoardNo1", trelloLists),
                new TrelloBoard("2", "testBoard", new ArrayList<>()));
        TrelloValidator trelloValidator = new TrelloValidator();

        //When
        List<TrelloBoard> testTrelloBoards = trelloValidator.validateTrelloBoards(trelloBoards);

        //Then
        assertEquals(1, testTrelloBoards.size());
    }

    @Test
    void testValidateCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard("TrelloCard", "TrelloCardDescription", "topPos", "TrelloList");

        //When
        trelloValidator.validateCard(trelloCard);

        //Then
        verify(trelloValidator, times(1)).validateCard(trelloCard);

    }
}
