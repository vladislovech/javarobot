package org.robotgame.gui.buildingInternalFrame.GameController;

public class TileMap {
    private Tile[][] tiles;
    private int width; // Ширина карты в тайлах
    private int height; // Высота карты в тайлах

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height]; // Создание пустой матрицы тайлов
        // Инициализация каждого тайла
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.tiles[x][y] = new Tile(); // Создаем новый объект тайла
            }
        }
    }

    // Метод для установки характеристик персонажа для определенного тайла
    public void setTileCharacteristics(int x, int y, Characteristics characteristics) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            tiles[x][y].setCharacteristics(characteristics);
        }
    }

    // Метод для получения характеристик персонажа для определенного тайла
    public Characteristics getTileCharacteristics(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tiles[x][y].getCharacteristics();
        }
        return null; // Возвращаем null в случае, если координаты выходят за пределы карты
    }

    // Геттеры и сеттеры для ширины и высоты карты
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // Пример класса для хранения характеристик персонажа
    public static class Characteristics {
        // Здесь можно определить любые характеристики персонажа, например, здоровье, атака, защита и т.д.
    }

    // Пример класса для представления тайла
    private static class Tile {
        private Characteristics characteristics;

        public Tile() {
            this.characteristics = new Characteristics(); // Инициализируем характеристики персонажа для каждого тайла
        }

        public Characteristics getCharacteristics() {
            return characteristics;
        }

        public void setCharacteristics(Characteristics characteristics) {
            this.characteristics = characteristics;
        }
    }
}
