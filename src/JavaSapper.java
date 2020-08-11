// Структура программы такова:
// Класс координат Coord, который связан с классом Ranges.
// Класс Ranges имеет статические методы, используемые в других методах.
// Перечисление Box хранит изображения.
// Класс Matrix для хранения всего поля. Классы Boomb и Flag для хранения нижнего и верхнего слоев соответственно.
// Перечисление GameState - статус игры.
// Класс Game, через который происходит обращение ко всем методам из основной программы.

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sapper.Box;
import sapper.Coord;
import sapper.Game;
import sapper.Ranges;

public class JavaSapper extends JFrame
{
    private Game game;

    private JPanel panel;
    private JLabel label;
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOOMBS = 10;
    private final int IMAGE_SIZE = 50;


    public static void main(String[] args)
    {
        new JavaSapper();
    }

    private JavaSapper()
    {
        game = new Game( COLS, ROWS, BOOMBS);
        game.start();
        setImage();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel()
    {
        label = new JLabel("Welcome!");
        add (label, BorderLayout.SOUTH);
    }

    private  void initPanel()
    {
        panel = new JPanel()
        {
            @Override
            protected void paintComponent (Graphics g){
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords())
                {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE,
                            coord.y * IMAGE_SIZE,
                            this);
                }

            }
        };

        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                label.setText (getMessage());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
        add (panel);
    }

    private String getMessage()
    {
        switch (game.getState())
        {
            case PLAYED: return "Good luck!";
            case BOMBED: return "You lose :(";
            case WINNER: return "Congratulation, you won!";
            default: return  "Welcome!";
        }
    }

    private void initFrame ()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle ("Java Sapper");
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
        pack();
        setLocationRelativeTo(null);
    }

    private void setImage ()
    {
        for (Box box : Box.values() )
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage (String name)
    {
        String filename = "img/" + name + ".png/";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
