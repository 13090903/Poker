package ru.vsu.csf.poker.interfaces;

import ru.vsu.csf.poker.enums.GameStages;
import ru.vsu.csf.poker.model.Card;

public interface CardGeneration {

    Card[] generateCards(GameStages stage);
}
