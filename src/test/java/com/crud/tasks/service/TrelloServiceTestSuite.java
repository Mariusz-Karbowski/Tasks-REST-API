package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TrelloServiceTestSuite {

    @InjectMocks
    TrelloService trelloService;

    @Mock
    TrelloClient trelloClient;

    @Mock
    AdminConfig adminConfig;

    @Mock
    SimpleEmailService emailService;

    @Test
    void testFetchTrelloBoards() {
        //Given
        TrelloBoardDto boardDto = new TrelloBoardDto("1", "testBoard", new ArrayList<>());
        List<TrelloBoardDto> boardDtoList = new ArrayList<>();
        boardDtoList.add(boardDto);

        when(trelloClient.getTrelloBoards()).thenReturn(boardDtoList);

        //When
        List<TrelloBoardDto> testList = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(1, testList.size());
    }

    @Test
    void testCreateTrelloCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("testTask", "testDescription",
                "topPos", "testID");
        CreatedTrelloCardDto createdCardDto = new CreatedTrelloCardDto("1", "testCard", "testURL");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdCardDto);

        //When
        CreatedTrelloCardDto cardDto = trelloService.createTrelloCard(trelloCardDto);

        //Then
        assertEquals("1", cardDto.getId());
        assertEquals("testCard", cardDto.getName());
        assertEquals("testURL", cardDto.getShortUrl());
    }
}
