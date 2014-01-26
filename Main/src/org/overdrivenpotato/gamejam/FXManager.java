package org.overdrivenpotato.gamejam;

/**
 * Created by marko on 25/01/14.
 */
public class FXManager {
    private static float screenShake = 0f;
    private static float pulse = 0f;
    private final static float pulseSpeed = 1f;

    private static boolean shaken = false;

    public static float pulse()
    {
        return pulse;
    }

    public static void update(float playerX, float playerY)
    {
        if(!shaken)
        {
            shaken = true;
            screenShake = playerY / 15f;
        }
        if(Math.abs(screenShake) <= 0.1)
        {
            shaken = false;
        }
        else
        {
            screenShake *= -0.7f;
        }

        pulse += pulseSpeed;
        if(pulse > 180)
            pulse = 0;
    }

    public static double getPulseX()
    {
        return Math.sin(pulse);
    }

    public static double getPulseY()
    {
        return Math.cos(pulse);
    }

    public static float getModX() {
        return (float) (getPulseX() + screenShake);
    }

    public static float getModY() {
        return (float) getPulseY();
    }
}
