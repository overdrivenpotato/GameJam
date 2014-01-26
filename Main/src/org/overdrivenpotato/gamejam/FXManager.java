package org.overdrivenpotato.gamejam;

import java.util.Date;

/**
 * Created by marko on 25/01/14.
 */
public class FXManager {
    private static float screenShake = 0f;
    private static float pulse = 0f;
    private final static float pulseSpeed = 1f;
    private static long lastTime;
    private static EntityPlayer player;

    private static boolean shaken = false;

    public static float pulse()
    {
        return pulse;
    }

    public static void startMusic()
    {
        screenShake = 0f;
        lastTime = new Date().getTime();
        selfUpdate();
    }

    public static void selfUpdate()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                lastTime = new Date().getTime();
                screenShake = player.getY() / 15f;
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.run();
            }
        }).start();
    }
    public static void update(float playerX, float playerY)
    {
        Date timeKeeper = new Date();
        if(timeKeeper.getTime() - lastTime > 1000)
        {
            System.out.println(timeKeeper.getTime() - lastTime > 250);
            System.out.println(timeKeeper.getTime());
            System.out.println(lastTime);
//            lastTime = timeKeeper.getTime();
            shaken = true;
//            screenShake = playerY / 15f;
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

    public static void setPlayer(EntityPlayer player) {
        FXManager.player = player;
    }
}
