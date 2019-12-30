package cs3500.animator.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.ITextableAnimatedShape;

/**
 * A view that composes a new interactive GUI with an SVG view.
 *
 * <p> This model is capable of handling the following functions: </p>
 * 1. Start, pause, resume, and restart (rewind to beginning and start)
 *    the animation with explicit user input (e.g. key press, button click, etc).
 * 2. Enable/disable looping: enabling looping should cause the animation to automatically loopback.
 * 3. Increase or decrease the speed of the animation, as it is being played,
 *    and immediately see the results.
 * 4. Exporting the animation that you see to an SVG file named by the user.
 **/

public class InteractiveGuiView implements IHybridView {

  private ISvgView svgView;
  private GuiView guiView;
  private JButton start;
  private JToggleButton pause;
  private JButton restart;
  private JButton exportSvg;
  private JToggleButton loop;
  private JButton increment;
  private JButton decrement;

  /**
   * Default constructor for interactiveGuiView.
   *
   * @param framesPerSecond int fps
   * @param output appendable
   */
  public InteractiveGuiView(int framesPerSecond, Appendable output) {
    this.svgView = new SvgView(framesPerSecond, output);
    this.guiView = new GuiView();
    JPanel listOfButtons = new JPanel();
    listOfButtons.setLayout(new FlowLayout());

    start = new JButton("Start Animation");
    start.setActionCommand("start");

    pause = new JToggleButton("Pause");
    pause.setActionCommand("pause");
    pause.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pause) {
          if (pause.isSelected()) {
            pause.setText("Resume");
            pause.setActionCommand("resume");
          } else {
            pause.setText("Pause");
            pause.setActionCommand("pause");
          }
        }
      }
    });


    restart = new JButton("Restart");
    restart.setActionCommand("restart");

    exportSvg = new JButton("Export as SVG");
    exportSvg.setActionCommand("svg");

    //resets SVG export boolean to false
    loop = new JToggleButton("Enable Looping");
    loop.setActionCommand("enableLoop");
    loop.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loop) {
          if (loop.isSelected()) {
            loop.setText("Disable Looping");
            loop.setActionCommand("disableLoop");
          } else {
            loop.setText("Enable Looping");
            loop.setActionCommand("enableLoop");
          }
        }
      }
    });

    increment = new JButton("+");
    increment.setSize(5, 5);
    increment.setActionCommand("increment");

    decrement = new JButton("-");
    decrement.setSize(5, 5);
    decrement.setActionCommand("decrement");

    listOfButtons.add(start);
    listOfButtons.add(pause);
    listOfButtons.add(restart);
    listOfButtons.add(exportSvg);
    listOfButtons.add(loop);
    listOfButtons.add(increment);
    listOfButtons.add(decrement);

    guiView.add(listOfButtons, BorderLayout.PAGE_END);
    guiView.setVisible(true);
  }

  @Override
  public void downloadSVG() {
    this.svgView.display();
  }

  @Override
  public void closeSVG() {
    this.svgView.close();
  }

  @Override
  public void setListeners(ActionListener clicks) {
    start.addActionListener(clicks);
    pause.addActionListener(clicks);
    restart.addActionListener(clicks);
    exportSvg.addActionListener(clicks);
    loop.addActionListener(clicks);
    increment.addActionListener(clicks);
    decrement.addActionListener(clicks);
  }

  @Override
  public void updateContents(Collection<ITextableAnimatedShape> shapes) {
    svgView.updateContents(new ArrayList<>(shapes));
  }

  @Override
  public void display() {
    guiView.jScrollPane.setVisible(true);
  }

  @Override
  public void close() {
    guiView.jScrollPane.setVisible(false);
  }

  @Override
  public String toString() {
    return "Interactive";
  }

  @Override
  public void updateScreen(ArrayList<IShape> shapes) {
    guiView.updateScreen(shapes);
  }
}
