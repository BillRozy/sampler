package com.company;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by FD on 28.04.2016.
 */
// CLASS Instrument keeper of wav sound, and have method to play it
class Instrument{
    public Instrument(String urlToSound){
        this.soundBank = openFile(urlToSound);
        clip = makeClip();
    }

    public void playSound()
    {
        try{
            long begin = System.currentTimeMillis();
            clip.setFramePosition(0); //устанавливаем указатель на старт
            clip.start(); //Поехали!!!
            //Если не запущено других потоков, то стоит подождать, пока клип не закончится
            //В GUI-приложениях следующие 3 строчки не понадобятся
            Thread.sleep(Sampler.getSampler().getDelay());
            clip.stop();
        }  catch (InterruptedException exc) {Thread.currentThread().interrupt();}
    }

    private Clip makeClip() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundBank);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            return clip;
        }catch(IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();}
        return null;
    }

    private static File openFile(String url){
        File soundFile = new File(url); //Звуковой файл
        return soundFile;
    }

    //PROPERTIES
    private File soundBank = null;
    private Clip clip = null;
}
