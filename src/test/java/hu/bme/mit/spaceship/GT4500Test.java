package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hu.bme.mit.spaceship.FiringMode;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimaryTorpedoStore;
  private TorpedoStore mockSecondaryTorpedoStore;

  @BeforeEach
  public void init() {
    this.mockPrimaryTorpedoStore = mock(TorpedoStore.class);
    this.mockSecondaryTorpedoStore = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimaryTorpedoStore, mockSecondaryTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success() {
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(0)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success() {
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Both_Empty() {
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(0)).fire(1);
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_One_Empty() {
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(0)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(0)).fire(1);
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Alternating_Fire_Success() {
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean firstShotResult = ship.fireTorpedo(FiringMode.SINGLE);
    boolean secondShotResult = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
    assertEquals(true, firstShotResult);
    assertEquals(true, secondShotResult);
  }

  @Test
  public void fireTorpedo_fireTorpedo_Alternating_Fire_One_Empty() {
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean firstShotResult = ship.fireTorpedo(FiringMode.SINGLE);
    boolean secondShotResult = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimaryTorpedoStore, times(2)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(2)).fire(1);
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
    assertEquals(true, firstShotResult);
    assertEquals(true, secondShotResult);
  }

  @Test
  public void fireTorpedo_Single_Primary_Empty() {
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(0)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Primary_Failure() {
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(false);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Second_Fire_Empty() {
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean firstShotResult = ship.fireTorpedo(FiringMode.SINGLE);

    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);

    boolean secondShotResult = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimaryTorpedoStore, times(2)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
    assertEquals(true, firstShotResult);
    assertEquals(false, secondShotResult);
  }

  @Test
  public void fireTorpedo_All_Secondary_Failure() {
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_Secondary_Empty() {
    // Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(0)).fire(1);
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Invalid_Firing_Mode() {
    //Arrange

    //Act
    boolean result = ship.fireTorpedo(FiringMode.INVALID_FIRING_MODE);

    //Assert
    verify(mockPrimaryTorpedoStore, times(0)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(0)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(0)).fire(1);
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
    assertEquals(false, result);
  }
}
