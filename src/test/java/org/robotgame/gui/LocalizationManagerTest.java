package org.robotgame.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalizationManagerTest {
    @Test
    public void TestChangeLanguageEN() {
        assertEquals(true, LocalizationManager.changeLanguage("EN"));
    }
    @Test
    public void TestChangeLanguageRU() {
        assertEquals(true, LocalizationManager.changeLanguage("RU"));
    }
    @Test
    public void TestGetString1() {
        assertEquals(true, LocalizationManager.changeLanguage("EN"));
        String encoded = "Are you sure you want to close the window?";
        assertEquals(encoded, LocalizationManager.getString("window.closing.message"));
    }
    @Test
    public void TestGetString2(){
        assertEquals(true, LocalizationManager.changeLanguage("RU"));
        String encoded = "Вы уверены, что хотите закрыть окно?";
        assertEquals(encoded, LocalizationManager.getString("window.closing.message"));
    }
}
