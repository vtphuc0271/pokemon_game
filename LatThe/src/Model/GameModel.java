/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Beans.NguoiChoi;
import Beans.NguoiChoiDAO;
import View.GameView;
import com.mysql.cj.protocol.Message;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import jdk.javadoc.internal.doclets.formats.html.markup.Text;

/**
 *
 * @author Administrator
 */
public class GameModel {

    public int sizeX[] = {3, 4, 6, 8, 6, 9, 10, 10, 12, 14};
    public int sizeY[] = {2, 3, 3, 3, 5, 4, 4, 5, 5, 5};
    public int manChoi = 1;
    public int diemSo = 0;
    public int thoiGianChoi = manChoi * 30;
    public Timer timerLatHinh, timerCountDown;
    public int[] width = {380, 500, 750, 990, 750, 1120, 1250, 850, 1010, 1150};
    public int[] height = {430, 600, 600, 600, 950, 775, 780, 650, 650, 650};
    public JProgressBar progressTime;
    public int countOfButton = getSizeX(manChoi) * getSizeY(manChoi);
    public JButton[] card = new JButton[countOfButton];
    public JLabel lbl_PointName = new JLabel("Point: ");
    public JLabel lbl_point = new JLabel(diemSo + "");
    public int time = 0;
    public int[] sortRandom = randomButton();
    public int dem = 0;
    public int selected1 = 0;
    public int selected2 = 0;
    public JFrame gameView = new JFrame();
    public JPanel paneCenter = new JPanel();
    public ActionListener clickButton;
    public int countCorrect = 0;
    GameView view;

    public JButton btn_XemDiem = new JButton("View Record");

    public JLabel getLbl_point() {
        return lbl_point;
    }

    public void setLbl_point(int diem) {
        this.lbl_point = new JLabel(diem + "");
    }

    public int getCountOfButton() {
        return countOfButton;
    }

    public JButton[] getCard() {
        return card;
    }

    public void setCountOfButton(int man) {
        this.countOfButton = getSizeX(man) * getSizeY(man);
    }

    public void setCard(int man) {
        this.card = new JButton[countOfButton];
    }

    public void setSortRandom() {
        this.sortRandom = randomButton();
    }

    public int getSizeX(int man) {
        return sizeX[man - 1];
    }

    public int getSizeY(int man) {
        return sizeY[man - 1];
    }

    public int getManChoi() {
        return manChoi;
    }

    public void setManChoi(int manChoi) {
        this.manChoi = manChoi;
    }

    public int getDiemSo() {
        return diemSo;
    }

    public int getThoiGianChoi() {
        return thoiGianChoi;
    }

    public void setDiemSo(int diemSo) {
        this.diemSo = diemSo;
    }

    public void setThoiGianChoi(int man) {
        this.thoiGianChoi = man * 30;
    }

    public void ChoiMoi() {
        setManChoi(1);
    }

    public void ChoiTiep() {
        setManChoi(manChoi++);
    }

    public final void Event() {
        timerLatHinh = new Timer(200, (ActionEvent e) -> {
            checkImage();
            timerLatHinh.stop();
        });

        timerCountDown = new Timer(1000, (ActionEvent e) -> {
            time++;
            progressTime.setValue(thoiGianChoi - time);

            if (thoiGianChoi == time) {
                timerCountDown.stop();
                try {
                    showDialogSavePoint("Điểm: " + lbl_point.getText() + "\n" + "Bạn Có Muốn Lưu Điểm không ?", "Thông Báo");
                } catch (IOException ex) {
                    Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            if (countCorrect == countOfButton) {
                timerCountDown.stop();
                try {
                    showDialogNextGame("Chúc Mừng bạn.\n"
                            + "Điểm: " + lbl_point.getText() + "\n"
                            + "Bạn có muốn chơi tiếp không?", "Thông báo");
                } catch (IOException ex) {
                    Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void showDialogNewGame(String message, String title) throws IOException {
        view = new GameView();
        int select = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                null, null);
        if (select == 0) {
            ChoiMoi();
            for (JButton item : card) {
                paneCenter.remove(item);
            }
            gameView.dispose();
            setDiemSo(0);
            view.HienThi(1, 0);
        } else {
            System.exit(0);
        }
    }

    public void showDialogSavePoint(String message, String title) throws IOException {
        int select = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                null, null);
        if (select == 0) {
            JFrame savePoint = new JFrame();
            JButton save = new JButton("Lưu");
            savePoint.setLocationRelativeTo(null);
            JLabel lbl_name = new JLabel("Tên Người Chơi");
            JTextField txt_name = new JTextField(10);
            savePoint.setResizable(false);
            JLabel lbl_savePoint = new JLabel("Point :" + diemSo);
            savePoint.setSize(300, 130);
            savePoint.setTitle("Lưu Điểm");
            JPanel paneSaveNorth = new JPanel();
            JPanel paneSaveCenter = new JPanel();
            JPanel paneSaveSouth = new JPanel();
            paneSaveNorth.add(lbl_name);
            paneSaveNorth.add(txt_name);
            paneSaveCenter.add(lbl_savePoint);
            paneSaveSouth.add(save);
            savePoint.add(paneSaveNorth, BorderLayout.NORTH);
            savePoint.add(paneSaveCenter, BorderLayout.CENTER);
            savePoint.add(paneSaveSouth, BorderLayout.SOUTH);
            save.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (txt_name.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Vui Lòng Nhập Tên");
                    } else {
                        NguoiChoi temp_NguoiChoi = new NguoiChoi(txt_name.getText(), diemSo);
                        NguoiChoiDAO temp_NguoiChoiDao = new NguoiChoiDAO();
                        boolean kq = temp_NguoiChoiDao.ThemDuLieu(temp_NguoiChoi);
                        if (kq == true) {
                            JOptionPane.showMessageDialog(null, "Lưu Điểm Thành Công");
                        } else {
                            JOptionPane.showMessageDialog(null, "Lưu Điểm không Thành Công");
                        }
                        savePoint.dispose();
                        try {
                            showDialogNewGame("Hết thời gian.\n"
                                    + "Điểm: " + lbl_point.getText() + "\n"
                                    + "Bạn có muốn chơi lại không?", "Thông báo");
                        } catch (IOException ex) {
                            Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            });
            savePoint.setVisible(true);
        } else {
            try {
                showDialogNewGame("Hết thời gian.\n"
                        + "Điểm: " + lbl_point.getText() + "\n"
                        + "Bạn có muốn chơi lại không?", "Thông báo");
            } catch (IOException ex) {
                Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void showDialogNextGame(String message, String title) throws IOException {
        view = new GameView();
        int select = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                null, null);
        if (select == 0) {
            setManChoi(getManChoi() + 1);
            for (JButton item : card) {
                paneCenter.remove(item);
            }
            gameView.dispose();
            view.HienThi(getManChoi() + 1, diemSo);
            System.out.println(diemSo);
        } else {
            System.exit(0);
        }
    }

    public void viewPoint() {
        NguoiChoiDAO temp = new NguoiChoiDAO();
        ArrayList<NguoiChoi> topNguoiChoi = temp.LayTopTheoDiem();
        JFrame viewPoint = new JFrame();
        Object[] colName = {"ID", "Tên Người Chơi", "Điểm Số"};
        DefaultTableModel tableModel = new DefaultTableModel(colName, 0);
        tableModel.addRow(colName);
        for (int i = 0; i < topNguoiChoi.size(); i++) {
            int idNguoiChoi = topNguoiChoi.get(i).getIdNguoiChoi();
            String tenNguoiChoi = topNguoiChoi.get(i).getTenNguoiChoi();
            int diemSo = topNguoiChoi.get(i).getDiemSo();
            Object[] objs = {idNguoiChoi, tenNguoiChoi, diemSo};
            tableModel.addRow(objs);
        }
        JTable ds = new JTable(tableModel);
        ds.getColumnModel().getColumn(0).setPreferredWidth(50);
        ds.getColumnModel().getColumn(1).setPreferredWidth(150);
        ds.getColumnModel().getColumn(2).setPreferredWidth(100);
        viewPoint.setLocationRelativeTo(null);
        viewPoint.setSize(300, 215);
        viewPoint.setResizable(false);

        viewPoint.add(ds, "North");
        viewPoint.setVisible(true);
    }

    public int[] randomButton() {
        int[][] random = new int[countOfButton][2];
        int[] temp;
        for (int i = 0;
                i < random.length;
                i++) {
            random[i][0] = i + 1;
            if (i + 1 > countOfButton / 2) {
                random[i][0] = i + 1 - (countOfButton / 2);
            }
            random[i][1] = 2;
//            System.out.println(arr[i] + " ");
        }
        temp = new int[countOfButton];
        int j = 0;
        Random rd = new Random();
        while (j < temp.length) {
            if (temp[j] == 0) {
                int k = rd.nextInt(countOfButton / 2);
                if (random[k][1] > 0) {
                    temp[j] = random[k][0];
                    random[k][1]--;
                    j++;
                }
            }
        }
        return temp;
    }

    public void changeImage(JButton selectedcard, int i) {
        selectedcard.setIcon(getIcon(sortRandom[i], manChoi));
        selectedcard.setIconTextGap(sortRandom[i]);
        if (dem == 0) {
            selected1 = i;
        }
        if (dem == 1 && i != selected1) {
            selected2 = i;
            timerLatHinh.start();
        }
        dem++;
    }

    public boolean checkImage() {
        boolean result = false;
        if (card[selected1].getIconTextGap() == (card[selected2].getIconTextGap())) {
            result = true;
            card[selected1].setVisible(false);
            card[selected2].setVisible(false);
            countCorrect += 2;
            diemSo += 10;
            lbl_point.setText(diemSo + "");
            selected1 = 0;
            selected2 = 0;
        } else {
            card[selected1].setIcon(getIcon(0, manChoi));
            card[selected2].setIcon(getIcon(0, manChoi));
            if (diemSo != 0) {
                diemSo -= 1;
            }
            lbl_point.setText(diemSo + "");
            selected1 = 0;
            selected2 = 0;
        }
        dem = 0;
        return result;
    }

    public static Icon getIcon(int index, int man) {

        if (man <= 7) {
            try {
                BufferedImage card = ImageIO.read(new File("img\\card" + index + ".jpg"));
                Icon icon = new ImageIcon(card.getScaledInstance(120, 170, BufferedImage.SCALE_SMOOTH));
                return icon;
            } catch (IOException e) {
                // TODO Auto-generated catch block

            }
        } else {
            try {
                BufferedImage card = ImageIO.read(new File("img\\card" + index + ".jpg"));
                Icon icon = new ImageIcon(card.getScaledInstance(80, 110, BufferedImage.SCALE_SMOOTH));
                return icon;
            } catch (IOException e) {
                // TODO Auto-generated catch block

            }
        }
        return null;
    }

}
