package dummytestobjects;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;

import static junit.framework.TestCase.assertEquals;

public class TestDummies {

  private DummyController dummyController;
  private ActionEvent clickButton1;
  private ActionEvent clickButton2;
  private ActionEvent clickButton3;
  private ActionEvent clickButton4;
  private ActionEvent clickButton5;
  private ActionEvent clickButton6;
  private ActionEvent clickButton7;
  private ActionEvent clickButton8;
  private ActionEvent clickButton9;


  /**
   * Initialized the clicks and dummies.
   */
  @Before
  public void initData() {

    DummyView dummyView = new DummyView(20, System.out);
    dummyController = new DummyController(
            null, dummyView, 20);
    clickButton1 = new ActionEvent(dummyView, 0, "svg-button-clicked");
    clickButton2 = new ActionEvent(dummyView, 1,
            "start-button-clicked");
    clickButton3 = new ActionEvent(dummyView, 2,
            "restart-button-clicked");
    clickButton4 = new ActionEvent(dummyView, 3,
            "loop-button-clicked");
    clickButton5 = new ActionEvent(dummyView, 4,
            "loop-button-unclicked");
    clickButton6 = new ActionEvent(dummyView, 5,
            "pause-button-clicked");
    clickButton7 = new ActionEvent(dummyView, 6,
            "resume-button-clicked");
    clickButton8 = new ActionEvent(dummyView, 7,
            "decrement-button-clicked");
    clickButton9 = new ActionEvent(dummyView, 8,
            "increment-button-clicked");
  }

  @Test
  public void testSVGClick() {
    assertEquals(dummyController.getButtonDescription(), "");
    dummyController.actionPerformed(clickButton1);
    assertEquals(dummyController.getButtonDescription(), "svg-button-clicked");
  }

  @Test
  public void testStartClick() {
    assertEquals(dummyController.getButtonDescription(), "");
    dummyController.actionPerformed(clickButton2);
    assertEquals(dummyController.getButtonDescription(), "start-button-clicked");
  }

  @Test
  public void testRestartClick() {
    assertEquals(dummyController.getButtonDescription(), "");
    dummyController.actionPerformed(clickButton3);
    assertEquals(dummyController.getButtonDescription(), "restart-button-clicked");
  }

  @Test
  public void testLoopClick() {
    assertEquals(dummyController.getButtonDescription(), "");
    dummyController.actionPerformed(clickButton4);
    assertEquals(dummyController.getButtonDescription(), "loop-button-clicked");
  }

  @Test
  public void testLoopUnClick() {
    assertEquals(dummyController.getButtonDescription(), "");
    dummyController.actionPerformed(clickButton5);
    assertEquals(dummyController.getButtonDescription(), "loop-button-unclicked");
  }


  @Test
  public void testPauseClick() {
    assertEquals(dummyController.getButtonDescription(), "");
    dummyController.actionPerformed(clickButton6);
    assertEquals(dummyController.getButtonDescription(), "pause-button-clicked");
  }

  @Test
  public void testPauseUnClick() {
    assertEquals(dummyController.getButtonDescription(), "");
    dummyController.actionPerformed(clickButton7);
    assertEquals(dummyController.getButtonDescription(), "resume-button-clicked");
  }

  @Test
  public void testIncrementClick() {
    assertEquals(dummyController.getButtonDescription(), "");
    dummyController.actionPerformed(clickButton8);
    assertEquals(dummyController.getButtonDescription(), "increment-button-clicked");
  }

  @Test
  public void testDecrementClick() {
    assertEquals(dummyController.getButtonDescription(), "");
    dummyController.actionPerformed(clickButton9);
    assertEquals(dummyController.getButtonDescription(), "decrement-button-clicked");
  }

}
