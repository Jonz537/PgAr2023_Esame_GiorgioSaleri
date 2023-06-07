package main;

import unibs.InputInterface;

public class UserInterface {

    public static Player createPlayer() {
        return new Player(InputInterface.readNotEmptyStringSingleWord("Insert your name: "));
    }

}
