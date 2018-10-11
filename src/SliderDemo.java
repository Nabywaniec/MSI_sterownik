import java.applet.Applet;
import java.awt.*;
import javax.swing.*;

public class SliderDemo extends Applet {
    int Gtemperature = 50;
    int Gpreference = 0;
    int Ghour = 12;
    int Gresult;

    int NUM_FRAMES = 3;
    ImageIcon[] images = new ImageIcon[NUM_FRAMES];
    JLabel picture;

    public SliderDemo() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JLabel sliderLabel = new JLabel("Temperatura", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JSlider temperature = new JSlider(JSlider.HORIZONTAL, 15, 80, 50);
        temperature.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
                Gtemperature = source.getValue();
            }
        });

        temperature.setMajorTickSpacing(10);
        temperature.setMinorTickSpacing(5);
        temperature.setPaintTicks(true);
        temperature.setPaintLabels(true);
        temperature.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        Font font = new Font("Serif", Font.ITALIC, 15);
        temperature.setFont(font);

        JLabel sliderLabel2 = new JLabel("Godzina", JLabel.CENTER);
        sliderLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSlider hour = new JSlider(JSlider.HORIZONTAL, 1, 24, 12);

        hour.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
                Ghour = source.getValue();
            }
        });

        hour.setMajorTickSpacing(12);
        hour.setMinorTickSpacing(2);
        hour.setPaintTicks(true);
        hour.setPaintLabels(true);
        hour.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        hour.setFont(font);

        JLabel sliderLabel3 = new JLabel("Preferencja", JLabel.CENTER);
        sliderLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSlider preference = new JSlider(JSlider.HORIZONTAL,-3, 3, 0);

        preference.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
                Gpreference = source.getValue();
                System.out.println(Gpreference);
            }
        });

        preference.setMajorTickSpacing(10);
        preference.setMinorTickSpacing(1);
        preference.setPaintTicks(true);
        preference.setPaintLabels(true);
        preference.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        preference.setFont(font);

        picture = new JLabel();
        picture.setHorizontalAlignment(JLabel.CENTER);
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        picture.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(10,10,10,10)));
        updatePicture(0); //display first frame

        add(sliderLabel);
        add(temperature);
        add(sliderLabel2);
        add(hour);
        add(sliderLabel3);
        add(preference);
        add(picture);
        //panel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

        new Thread(() -> {
            while (true) {
                try {
                    double result = FuzzyExample.evaluate(Gtemperature, Gpreference, Ghour);
                    Gresult = (int)result;
                    System.out.println(result);
                    if (result < -1) updatePicture(2);
                    else if (result > 1) updatePicture(0);
                    else updatePicture(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(100*3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            int a=0;
            while(true) {
                a=(a+1)%100;
                if(a%1 == 0) {
                    Gtemperature += Gresult;
                    temperature.setValue(Gtemperature);
                }
                if(a==0){
                    Ghour=(Ghour+1)%24;
                    hour.setValue(Ghour);
                }
                try {
                    Thread.sleep(100*3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /** Update the label to display the image for the current frame. */
    protected void updatePicture(int frameNum) {
        if (images[frameNum] == null) {
            images[frameNum] = createImageIcon("images/pict/T"
                    + frameNum
                    + ".png");
        }

        if (images[frameNum] != null) {
            picture.setIcon(images[frameNum]);
        } else { //image not found
            picture.setText("image #" + frameNum + " not found");
        }
    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = SliderDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }


    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("SliderDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SliderDemo animator = new SliderDemo();

        //Add content to the window.
        frame.add(animator, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
