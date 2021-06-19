package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "ListNo1", true));
        trelloBoardDtos.add(new TrelloBoardDto("1", "BoardNo1", trelloListDtos));

        //When
        List<TrelloBoard> testBoard = trelloMapper.mapToBoards(trelloBoardDtos);

        //Then
        assertEquals(trelloBoardDtos.get(0).getId(), testBoard.get(0).getId());
        assertEquals(trelloBoardDtos.get(0).getName(), testBoard.get(0).getName());
        assertEquals(trelloBoardDtos.get(0).getLists().size(), testBoard.get(0).getLists().size());
    }

    @Test
    public void testMapToBoardsDto() {
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "ListNo1", true));
        trelloBoards.add(new TrelloBoard("1", "BoardNo1", trelloLists));

        //When
        List<TrelloBoardDto> testBoard = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertEquals(trelloBoards.get(0).getId(), testBoard.get(0).getId());
        assertEquals(trelloBoards.get(0).getName(), testBoard.get(0).getName());
        assertEquals(trelloBoards.get(0).getLists().size(), testBoard.get(0).getLists().size());

    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "ListNo1", true));

        //When
        List<TrelloList> testList = trelloMapper.mapToList(trelloListDtos);

        //Then
        assertEquals(trelloListDtos.get(0).getId(), testList.get(0).getId());
        assertEquals(trelloListDtos.get(0).getName(), testList.get(0).getName());
        assertEquals(trelloListDtos.get(0).isClosed(), testList.get(0).isClosed());
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "ListNo1", true));

        //When
        List<TrelloListDto> testList = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertEquals(trelloLists.get(0).getId(), testList.get(0).getId());
        assertEquals(trelloLists.get(0).getName(), testList.get(0).getName());
        assertEquals(trelloLists.get(0).isClosed(), testList.get(0).isClosed());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("CardNo1", "Description", "top", "1");

        //When
        TrelloCard testCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals(trelloCardDto.getName(), testCard.getName());
        assertEquals(trelloCardDto.getDescription(), testCard.getDescription());
        assertEquals(trelloCardDto.getPos(), testCard.getPos());
        assertEquals(trelloCardDto.getListId(), testCard.getListId());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("CardNo1", "Description", "top", "1");

        //When
        TrelloCardDto testCard = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals(trelloCard.getName(), testCard.getName());
        assertEquals(trelloCard.getDescription(), testCard.getDescription());
        assertEquals(trelloCard.getPos(), testCard.getPos());
        assertEquals(trelloCard.getListId(), testCard.getListId());
    }
}
