package simulator.view;

import simulator.control.Controller;
import simulator.model.*;
import simulator.model.Event;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MapByRoadComponent extends JPanel implements TrafficSimObserver {


    private static final long serialVersionUID = 1L;

    private static final int _JRADIUS = 10;

    private static final Color _BG_COLOR = Color.WHITE;
    private static final Color _JUNCTION_COLOR = Color.BLUE;
    private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
    private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
    private static final Color _RED_LIGHT_COLOR = Color.RED;

    private RoadMap _map;

    private Image _car;

    MapByRoadComponent(Controller ctrl) {
        initGUI();
        ctrl.addObserver(this);
    }

    private void initGUI() {
        _car = loadImage("car_front.png");
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        super.setPreferredSize(new Dimension(300, 200));
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // clear with a background color
        g.setColor(_BG_COLOR);
        g.clearRect(0, 0, getWidth(), getHeight());

        if (_map == null || _map.getJunctions().size() == 0) {
            g.setColor(Color.red);
            g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
        } else {
            updatePrefferedSize();
            drawMap(g);
        }
    }

    private void drawMap(Graphics g) {
        drawRoads(g);
    }

    private void drawRoads(Graphics g) {
        for (int i = 0; i < _map.getRoads().size(); i++) {
            Road r  = _map.getRoads().get(i);
            int greendIdx = r.getDest().getGreenLightIndex();

            int x1 = 50;
            int y = (i + 1) * 50;
            int x2 = this.getWidth() - 100;


            int roadColorValue = 200
                    - (int) (200.0 * Math.min(1.0, (double) r.getTotalCO2() / (1.0 + (double) r.getContLimit())));
            Color roadColor = new Color(roadColorValue, roadColorValue, roadColorValue);


            drawLine(g, x1, y, x2, roadColor, r.getId());
            drawExtremeJunctions(g, r, x1, x2, y, greendIdx);
            drawVehicles(g, r, x1, x2, y);
            drawWeather(g, x2, y, r);
            drawCont(g, x2, y, r);
        }

    }


    private void drawWeather(Graphics g, int x, int y, Road r) {
        Image wImage;
        Weather w = r.getWeather();
        if (w == Weather.SUNNY ) {
            wImage = loadImage("sun.png");
        } else if(w == Weather.CLOUDY) {
            wImage = loadImage("cloud.png");
        } else if(w == Weather.RAINY) {
            wImage = loadImage("rain.png");
        } else if(w == Weather.STORM) {
            wImage = loadImage("storm.png");
        } else {
            wImage = loadImage("wind.png");
        }

        g.drawImage(wImage, x + 20, y - 25, 32, 32, this);
    }

    private void drawCont(Graphics g, int x, int y, Road r) {
        int A = r.getTotalCO2();
        int B = r.getContLimit();
        int C = (int) Math.floor(Math.min((double) A / (1.0 + (double) B), 1.0) / 0.19);
        String imgName = "cont_" + C + ".png";
        Image contImg = loadImage(imgName);

        g.drawImage(contImg, x + 50, y-25, 32, 32, this);

    }



    private void drawVehicles(Graphics g, Road r, int x1, int x2, int y) {
        for (Vehicle v : r.getVehicles()) {
            if (v.getStatus() != VehicleStatus.ARRIVED) {
                // The calculation below compute the coordinate (vX,vY) of the vehicle on the
                // corresponding road. It is calculated relatively to the length of the road, and
                // the location on the vehicles

                int A = v.getLocation();
                int B = r.getLength();
                int x = x1 + (int) ((x2 - x1) * ((double) A / (double) B));

                // Choose a color for the vehcile's label and background, depending on its
                // contamination class
                int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
                g.setColor(new Color(0, vLabelColor, 0));

                // draw an image of a car (with circle as background) and it identifier
                g.fillOval(x - 1, y - 6, 14, 14);
                g.drawImage(_car, x, y - 6, 16, 16, this);
                g.drawString(v.getId(), x, y - 6);


            }
        }
    }

    private void drawExtremeJunctions(Graphics g, Road r, int x1, int x2, int y, int greenIdx) {
        Junction jSrc = r.getSrc();
        Junction jDest = r.getDest();

        // draw a circle with center at (x,y) with radius _JRADIUS
        g.setColor(_JUNCTION_COLOR);
        g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
        g.setColor(_JUNCTION_LABEL_COLOR);
        g.drawString(jSrc.getId(), x1, y-10);


        Color destJunctionColor = _RED_LIGHT_COLOR;
        if (greenIdx != -1 && r.equals(r.getDest().getInRoads().get(greenIdx))) {
            destJunctionColor = _GREEN_LIGHT_COLOR;
        }


        // draw a circle with center at (x,y) with radius _JRADIUS
        g.setColor(destJunctionColor);
        g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
        g.setColor(_JUNCTION_LABEL_COLOR);
        g.drawString(jDest.getId(), x2, y-10);

    }

    // this method is used to update the preffered and actual size of the component,
    // so when we draw outside the visible area the scrollbars show up
    private void updatePrefferedSize() {
        int maxW = 200;
        int maxH = 200;
        for (Junction j : _map.getJunctions()) {
            maxW = Math.max(maxW, j.getX());
            maxH = Math.max(maxH, j.getY());
        }
        maxW += 20;
        maxH += 20;
        if (maxW > getWidth() || maxH > getHeight()) {
            setPreferredSize(new Dimension(maxW, maxH));
            setSize(new Dimension(maxW, maxH));
        }
    }

    // This method draws a line from (x1,y1) to (x2,y2) with an arrow.
    // The arrow is of height h and width w.
    // The last two arguments are the colors of the arrow and the line
    private void drawLine(//
                                   Graphics g, //
                                   int x1, int y, //
                                   int x2, //
                                   Color lineColor, String id) {

        g.setColor(lineColor);
        g.drawLine(x1, y, x2, y);
        g.setColor(Color.BLACK);
        g.drawString(id, x1 - 20, y);
    }

    // loads an image from a file
    private Image loadImage(String img) {
        Image i = null;
        try {
            return ImageIO.read(new File("TrafficSimulatorTP/resources/icons/" + img));
        } catch (IOException e) {
        }
        return i;
    }

    public void update(RoadMap map) {
        SwingUtilities.invokeLater(() -> {
            _map = map;
            repaint();
        });
    }

    @Override
    public void onAdvanceStart(RoadMap map, java.util.List<simulator.model.Event> events, int time) {
    }

    @Override
    public void onAdvanceEnd(RoadMap map, java.util.List<simulator.model.Event> events, int time) {
        update(map);
    }

    @Override
    public void onEventAdded(RoadMap map, java.util.List<simulator.model.Event> events, simulator.model.Event e, int time) {
        update(map);
    }

    @Override
    public void onReset(RoadMap map, java.util.List<simulator.model.Event> events, int time) {
        update(map);
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        update(map);
    }

    @Override
    public void onError(String err) {
    }
}
