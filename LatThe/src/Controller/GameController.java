/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.GameModel;
import View.GameView;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class GameController {
    public static void main(String args[]) throws IOException {
        GameModel model = new GameModel();
        GameView view = new GameView();
        view.HienThi(model.getManChoi(),model.getDiemSo());
    }
}
