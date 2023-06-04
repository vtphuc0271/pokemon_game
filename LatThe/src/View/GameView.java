/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.GameModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class GameView {

    private final GameModel model = new GameModel();

    public void HienThi(int man, int diem) throws IOException {
        model.setManChoi(man);
        model.setCountOfButton(man);
        model.setCard(man);
        model.setSortRandom();
        model.setThoiGianChoi(man);
        model.setLbl_point(diem);
        model.setDiemSo(diem);
        // int point = model.diemSo;
        // int sortRandom[] = model.randomButton();
        model.time = 0;
        model.countCorrect = 0;
        model.gameView.setSize(model.width[man - 1], model.height[man - 1]);

        model.gameView.setResizable(false);
        model.gameView.setTitle("Game Lật Hình - Võ Tấn Phúc");
        model.gameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        model.gameView.setLocationRelativeTo(null);

        //north
        model.progressTime = new JProgressBar(0, model.thoiGianChoi);
        model.progressTime.setValue(model.thoiGianChoi);
        model.progressTime.setForeground(Color.orange);
        model.gameView.add(model.progressTime, "North");

        //center
        model.paneCenter.setLayout(new GridLayout(model.getSizeY(man), model.getSizeX(man)));
        model.gameView.add(model.paneCenter, BorderLayout.CENTER);

        //hien thi
        int i;
        for (i = 0; i < model.card.length; i++) {
            final int a = i;
            model.card[i] = new JButton();
            model.card[i].setIcon(GameModel.getIcon(0, man));
            model.card[i].setBorder(null);
            model.card[i].setMargin(new Insets(0, 0, 0, 0));
            model.clickButton = (ActionEvent e) -> {
                model.timerCountDown.start();
                model.changeImage(model.card[a], a);
            };
            model.card[i].addActionListener(model.clickButton);
            model.paneCenter.add(model.card[i]);
        }

        //south
        JPanel paneSouth = new JPanel();
        model.gameView.add(paneSouth, BorderLayout.SOUTH);
        paneSouth.add(model.btn_XemDiem);
           model.btn_XemDiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.viewPoint();
            }
        });
        model.lbl_PointName.setFont(new Font("Cambria", Font.BOLD, 20));
        paneSouth.add(model.lbl_PointName);
        model.lbl_point.setFont(new Font("Cambria", Font.BOLD, 20));
        paneSouth.add(model.lbl_point);
        model.gameView.setVisible(true);
        //goi ham khac
        model.Event();
    }
}
