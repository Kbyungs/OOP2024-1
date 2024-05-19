// enjoy mine game by dr.han
// ToDo list
// 1. statistic (#success, #fail)
// 2. prevent same trial

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class MineClientGUI extends JFrame {
    static int inPort = 9999;
    static String address = "192.168.0.6";
    static public PrintWriter out;
    static public BufferedReader in;
    static int width = 0;
    static int num_mine = 0;
    static Map map;
    static int find = 0;
    static int totalTry = 0;
    static public Socket socket;

    public Container cont;
    public JPanel p0, p1, p2, p3;
    public JSlider slider0, slider1;
    public JButton b_map, b_retry;

    public JLabel l_try, l_success, l_left;
    public JButton[] buttons;
    public boolean[] clicked;

    public ImageIcon icon = new ImageIcon("../img/marioQbox.jpeg");

    public static void main(String[] args) {
        MineClientGUI game = new MineClientGUI();
    }

    public MineClientGUI() {
        setTitle("MineClientGUI");
        setSize(500, 400);
        setLocation(150, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cont = getContentPane();
        cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));

        p0 = new JPanel();
        p0.setBackground(Color.lightGray);
        p0.setLayout(new FlowLayout());

        p1 = new JPanel();
        p1.setBackground(Color.lightGray);
        p1.setLayout(new GridLayout(1, 1));

        p2 = new JPanel();
        p2.setBackground(Color.lightGray);
        p2.setLayout(new FlowLayout());

        p3 = new JPanel();
        p3.setBackground(Color.lightGray);
        p3.setLayout(new FlowLayout());

        slider0 = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        slider0.setMinorTickSpacing(1);
        slider0.setMajorTickSpacing(1);
        slider0.setPaintTicks(true);
        slider0.setPaintLabels(true);
        slider0.addChangeListener(new MyChangeListener());
        slider1 = new JSlider();
        slider1.addChangeListener(new MyChangeListener());

        b_map = new JButton("Create Map");
        b_map.setBackground(Color.YELLOW);
        b_map.addActionListener(new MyActionListener0());

        b_retry = new JButton("RETRY");
        b_retry.addActionListener(new MyActionListener2());
        l_success = new JLabel("find : " + find);
        l_try = new JLabel("try : " + totalTry);

        l_left = new JLabel("\"" + (num_mine - find) + "\" mines left");
        l_left.setFont(new Font("Arial", Font.PLAIN, 20));

        p0.add(slider0);
        p0.add(slider1);
        p0.add(b_map);

        p1.add(l_success);
        p1.add(l_try);

        p2.add(l_try);
        p2.add(l_success);
        p2.add(b_retry);

        p3.add(l_left);

        cont.add(p2);
        cont.add(p3);
        cont.add(p0);
        cont.add(p1);
        pack();

        setVisible(true);
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
    }

    class MyChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider s = (JSlider) e.getSource();
            if (s == slider0) {
                width = (int) s.getValue();
                slider1.setMaximum(width * width);
                slider1.setMinorTickSpacing(1);
                slider1.setMajorTickSpacing(5);
                slider1.setPaintTicks(true);
                slider1.setPaintLabels(true);
            } else
                num_mine = (int) s.getValue();
        }
    }

    class MyActionListener0 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                socket = new Socket(address, inPort);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println(width + "," + num_mine);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            p1.removeAll();

            // 새로운 맵 객체 생성
            map = new Map(width, num_mine);

            l_left.setText("\"" + (num_mine - find) + "\" mines left");
            p1.setLayout(new GridLayout(width, width));
            buttons = new JButton[width * width];
            clicked = new boolean[width * width];
            for (int i = 0; i < width * width; i++) {
                buttons[i] = new JButton("" + i);
                buttons[i].setForeground(Color.white);
                buttons[i].setIcon(icon);
                buttons[i].setPreferredSize(new Dimension(30, 30));
                buttons[i].addActionListener(new MyActionListener1());
                p1.add(buttons[i]);
                clicked[i] = false;
            }
            p0.setVisible(false);
            p1.setVisible(true);
            p2.setVisible(true);
            p3.setVisible(true);
        }
    }

    class MyActionListener1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();
            int i = Integer.parseInt(s);
            int x = i / width;
            int y = i % width;

            if (clicked[i]) {
                // 이미 클릭된 경우
                return;
            }
            clicked[i] = true;

            try {
                out.println(x + "," + y);
                String msg = in.readLine();
                int result = Integer.parseInt(msg);
                JButton b = (JButton) e.getSource();

                if (result >= 0) {
                    b.setText(result + "");
                    b.setForeground(Color.black);
                    map.updateMap(x, y);
                    totalTry++;
                    l_try.setText("try : " + totalTry);
                } else {
                    b.setText("O");
                    b.setForeground(Color.black);
                    totalTry++;
                    find++;
                    l_success.setText("find : " + find);
                    l_try.setText("try : " + totalTry);
                    if (num_mine != find)
                        l_left.setText("\"" + (num_mine - find) + "\" mines left");
                    else
                        l_left.setText("축하합니다!!!!");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    class MyActionListener2 implements ActionListener { // retry
        public void actionPerformed(ActionEvent e) {
            find = 0;
            totalTry = 0;
            l_success.setText("find : " + find);
            l_try.setText("try : " + totalTry);
            p2.setVisible(false);
            p1.setVisible(false);
            p0.setVisible(true);
            p3.setVisible(false);
            map = null;
            buttons = null;
            out.println("retry");
        }
    }
}
