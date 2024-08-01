package org.example;

public class Main {
    public static void main(String[] args) {
        // Play background music
        String musicFilePath = "CasinoJazz.wav";
        PlayMusic musicPlayer = new PlayMusic();
        musicPlayer.playMusic(musicFilePath);

        // Initialize and start the game
        GameRunner game = new GameRunner();
        game.playRound();
    }
}
