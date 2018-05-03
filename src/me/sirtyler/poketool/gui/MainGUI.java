package me.sirtyler.poketool.gui;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainGUI extends JDialog {
    private JPanel contentPane;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JComboBox comboBox6;
    private JComboBox comboBox7;
    private JCheckBox isShinyCheckBox1;
    private JCheckBox isShinyCheckBox2;
    private JCheckBox isShinyCheckBox3;
    private JCheckBox isShinyCheckBox4;
    private JCheckBox isShinyCheckBox5;
    private JCheckBox isShinyCheckBox6;
    private JButton buttonUpdate;
    private JButton buttonClose;
    private JTextField shinyHuntingTargetTextField;
    private JTextField pokemonPartyDisplayTextField;
    private JTextField textField1;
    private JButton buttonPlus;
    private JButton buttonMinus;
    private JButton resetButton;

    private static final File dir = new File(System.getProperty("user.dir"));
    private static final File party = new File(dir.getPath() + File.separator + "party");
    private static final File shiny = new File(dir.getPath() + File.separator + "shiny_hunt");

    private static final String base = "http://play.pokemonshowdown.com/sprites/xyani/";
    private static final String base_shiny = "http://play.pokemonshowdown.com/sprites/xyani-shiny/";

    private static int encounter = 0;
    private static String upKey = "PLUS";
    private static String downKey = "MINUS";
    private static String resetKey = "DIVIDE";

    public MainGUI() {
        super(new DummyFrame("PokeTool - Streamer"));

        setTitle("PokeTool - Streamer");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonUpdate);

        registerKey("PLUS", "MINUS", "DIVIDE");

        buttonUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        buttonPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                encounter++;
                textField1.setText(String.valueOf(encounter));
                writeEncounters();
            }
        });

        buttonMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                encounter--;
                textField1.setText(String.valueOf(encounter));
                writeEncounters();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                encounter = 0;
                textField1.setText(String.valueOf(encounter));
                writeEncounters();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            if(!party.exists()) party.mkdir();
            if(!shiny.exists()) shiny.mkdir();

            URL pk1 = new URL((isShinyCheckBox1.isSelected() ? base_shiny : base) + String.valueOf(comboBox1.getSelectedItem()) + ".gif");
            URL pk2 = new URL((isShinyCheckBox2.isSelected() ? base_shiny : base) + String.valueOf(comboBox2.getSelectedItem()) + ".gif");
            URL pk3 = new URL((isShinyCheckBox3.isSelected() ? base_shiny : base) + String.valueOf(comboBox3.getSelectedItem()) + ".gif");
            URL pk4 = new URL((isShinyCheckBox4.isSelected() ? base_shiny : base) + String.valueOf(comboBox4.getSelectedItem()) + ".gif");
            URL pk5 = new URL((isShinyCheckBox5.isSelected() ? base_shiny : base) + String.valueOf(comboBox5.getSelectedItem()) + ".gif");
            URL pk6 = new URL((isShinyCheckBox6.isSelected() ? base_shiny : base) + String.valueOf(comboBox6.getSelectedItem()) + ".gif");

            URL pkSH = new URL(base_shiny + String.valueOf(comboBox7.getSelectedItem() + ".gif"));

            InputStream in = pk1.openStream();
            OutputStream out = new FileOutputStream(party.getPath() + File.separator + "pokemon_one.gif");
            in.transferTo(out);
            out.flush();

            in.close();
            out.close();

            in = pk2.openStream();
            out = new FileOutputStream(party.getPath() + File.separator + "pokemon_two.gif");
            in.transferTo(out);
            out.flush();

            in.close();
            out.close();

            in = pk3.openStream();
            out = new FileOutputStream(party.getPath() + File.separator + "pokemon_three.gif");
            in.transferTo(out);
            out.flush();

            in.close();
            out.close();

            in = pk4.openStream();
            out = new FileOutputStream(party.getPath() + File.separator + "pokemon_four.gif");
            in.transferTo(out);
            out.flush();

            in.close();
            out.close();

            in = pk5.openStream();
            out = new FileOutputStream(party.getPath() + File.separator + "pokemon_five.gif");
            in.transferTo(out);
            out.flush();

            in.close();
            out.close();

            in = pk6.openStream();
            out = new FileOutputStream(party.getPath() + File.separator + "pokemon_six.gif");
            in.transferTo(out);
            out.flush();

            in.close();
            out.close();

            in = pkSH.openStream();
            out = new FileOutputStream(shiny.getPath() + File.separator + "target.gif");
            in.transferTo(out);
            out.flush();

            in.close();
            out.close();

            List<String> lines = new ArrayList<String>();
            lines.add(String.valueOf(comboBox1.getSelectedIndex()));
            lines.add(String.valueOf(isShinyCheckBox1.isSelected()));
            lines.add(String.valueOf(comboBox2.getSelectedIndex()));
            lines.add(String.valueOf(isShinyCheckBox2.isSelected()));
            lines.add(String.valueOf(comboBox3.getSelectedIndex()));
            lines.add(String.valueOf(isShinyCheckBox3.isSelected()));
            lines.add(String.valueOf(comboBox4.getSelectedIndex()));
            lines.add(String.valueOf(isShinyCheckBox4.isSelected()));
            lines.add(String.valueOf(comboBox5.getSelectedIndex()));
            lines.add(String.valueOf(isShinyCheckBox5.isSelected()));
            lines.add(String.valueOf(comboBox6.getSelectedIndex()));
            lines.add(String.valueOf(isShinyCheckBox6.isSelected()));
            lines.add(String.valueOf(comboBox7.getSelectedIndex()));

            Path file = Paths.get(dir.getPath() + File.separator + "last_used.txt");
            Files.write(file, lines, Charset.forName("UTF-8"));

            encounter = Integer.valueOf(textField1.getText());
            writeEncounters();
        } catch (Exception ex) {
            log("Unable to Write File");
            ex.printStackTrace();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private static void writeEncounters() {
        try {
            if (!shiny.exists()) shiny.mkdir();

            List<String> lines = new ArrayList<String>();
            lines.add(String.valueOf(encounter));
            lines.add(String.valueOf(upKey));
            lines.add(String.valueOf(downKey));
            lines.add(String.valueOf(resetKey));

            Path file = Paths.get(shiny.getPath() + File.separator + "encounters.txt");
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch(Exception ex) {
            log("Unable to Write File");
            ex.printStackTrace();
        }
    }

    private void loadEncounters() throws IOException {
        Path file = Paths.get(shiny.getPath() + File.separator + "encounters.txt");
        if(Files.exists(file)) {
            List<String> list = Files.readAllLines(file);

            encounter = Integer.valueOf(list.get(0));
            upKey = list.get(1);
            downKey = list.get(2);
            resetKey = list.get(3);

            textField1.setText(String.valueOf(encounter));
        } else return;
    }

    public static void main(String[] args) {
        MainGUI dialog = new MainGUI();

        try {
            Document doc = Jsoup.connect(base).get();
            log(doc.title());

            Elements fList = doc.select("td a");
            fList.remove(0);

            String[] sList = new String[fList.size()];
            int i = 0;
            for (Element file : fList) {
                //log(file.attr("href"));
                sList[i++] = file.attr("href").replaceAll(".gif", "");
            }

            dialog.addPokeData(sList);
            dialog.loadLast();
            dialog.loadEncounters();

        } catch(Exception ex) {
            log("Error, Unable to Process");
            ex.printStackTrace();
        }

        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private static void log(String s) {
        System.out.println(s);
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (!visible) {
            ((DummyFrame)getParent()).dispose();
        }
    }

    public void addPokeData(String[] inputArray) {
        DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>(inputArray);
        comboBox1.setModel(model1);
        DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>(inputArray);
        comboBox2.setModel(model2);
        DefaultComboBoxModel<String> model3 = new DefaultComboBoxModel<>(inputArray);
        comboBox3.setModel(model3);
        DefaultComboBoxModel<String> model4 = new DefaultComboBoxModel<>(inputArray);
        comboBox4.setModel(model4);
        DefaultComboBoxModel<String> model5 = new DefaultComboBoxModel<>(inputArray);
        comboBox5.setModel(model5);
        DefaultComboBoxModel<String> model6 = new DefaultComboBoxModel<>(inputArray);
        comboBox6.setModel(model6);
        DefaultComboBoxModel<String> model7 = new DefaultComboBoxModel<>(inputArray);
        comboBox7.setModel(model7);
    }

    private void loadLast() throws IOException {
        Path file = Paths.get(dir.getPath() + File.separator + "last_used.txt");
        if(Files.exists(file)) {
            List<String> list = Files.readAllLines(file);

            comboBox1.setSelectedIndex(Integer.valueOf(list.get(0)));
            isShinyCheckBox1.setSelected(Boolean.valueOf(list.get(1)));
            comboBox2.setSelectedIndex(Integer.valueOf(list.get(2)));
            isShinyCheckBox2.setSelected(Boolean.valueOf(list.get(3)));
            comboBox3.setSelectedIndex(Integer.valueOf(list.get(4)));
            isShinyCheckBox3.setSelected(Boolean.valueOf(list.get(5)));
            comboBox4.setSelectedIndex(Integer.valueOf(list.get(6)));
            isShinyCheckBox4.setSelected(Boolean.valueOf(list.get(7)));
            comboBox5.setSelectedIndex(Integer.valueOf(list.get(8)));
            isShinyCheckBox5.setSelected(Boolean.valueOf(list.get(9)));
            comboBox6.setSelectedIndex(Integer.valueOf(list.get(10)));
            isShinyCheckBox6.setSelected(Boolean.valueOf(list.get(11)));
            comboBox7.setSelectedIndex(Integer.valueOf(list.get(12)));
        } else return;
    }

    private void registerKey(String up, String down, String reset) {
        Provider provider = Provider.getCurrentProvider(false);

        upKey = up;
        downKey = down;
        resetKey = reset;

        provider.register(KeyStroke.getKeyStroke(upKey), new HotKeyListener() {
            public void onHotKey(HotKey hotKey) {
                encounter++;
                textField1.setText(String.valueOf(encounter));
                writeEncounters();
            }
        });

        provider.register(KeyStroke.getKeyStroke(downKey), new HotKeyListener() {
            public void onHotKey(HotKey hotKey) {
                encounter--;
                textField1.setText(String.valueOf(encounter));
                writeEncounters();
            }
        });

        provider.register(KeyStroke.getKeyStroke(resetKey), new HotKeyListener() {
            public void onHotKey(HotKey hotKey) {
                encounter = 0;
                textField1.setText(String.valueOf(encounter));
                writeEncounters();
            }
        });
    }
}

class DummyFrame extends JFrame {
    DummyFrame(String title) {
        super(title);
        setUndecorated(true);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}