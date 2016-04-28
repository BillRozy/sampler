package com.company;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.sound.sampled.*;

public class Main {
    public static void main(String[] args) {
        // write your code here

        Sampler myApp = Sampler.getSampler();
        Pattern firstPattern = new Pattern();
        Pattern secondPattern = new Pattern();
        myApp.addPattern(firstPattern);
        myApp.addPattern(secondPattern);
        myApp.setPatternActive(firstPattern);
        myApp.getActivePattern().addTrack("kick");
        myApp.getActivePattern().addTrack("snare");
        myApp.getActivePattern().addTrack("hh");
        myApp.getPattern(1).getTrack(1).makeHitActive(1,5,9,13);
        myApp.getPattern(1).getTrack(2).makeHitActive(3,7,11,15);
        myApp.getPattern(1).getTrack(3).makeHitActive(1,3,5,7,9,11,13,15);
        myApp.getActivePattern().getTrack(1).connectInstrument("H2Sv2 - THKL - Kick(0004).wav");
        myApp.getActivePattern().getTrack(2).connectInstrument("H2Sv3 - THSL - Snare(0003).wav");
        myApp.getActivePattern().getTrack(3).connectInstrument("H2Sv4 - THHL - HiHat(0006).wav");
        myApp.setPatternActive(secondPattern);
        myApp.getActivePattern().addTrack("kick");
        myApp.getActivePattern().addTrack("snare");
        myApp.getActivePattern().getTrack(1).connectInstrument("H2Sv2 - THKL - Kick(0004).wav");
        myApp.getActivePattern().getTrack(2).connectInstrument("H2Sv3 - THSL - Snare(0003).wav");
        myApp.getPattern(2).getTrack(1).makeHitActive(1,2,5,6,9,10,13,14);
        myApp.getPattern(2).getTrack(2).makeHitActive(3,7,11,15);
        myApp.setPatternActive(firstPattern);
        myApp.play();
        try{Thread.sleep(4000);
        }catch(InterruptedException exc){}
        myApp.stop();
        try{Thread.sleep(2000);
        }catch(InterruptedException exc){}
        myApp.play();
        try{Thread.sleep(2000);
        }catch(InterruptedException exc){}
        myApp.stop();
        myApp.setPatternActive(secondPattern);
        try{Thread.sleep(1000);
        }catch(InterruptedException exc){}
         myApp.setPatternActive(secondPattern);
        myApp.play();
        try{Thread.sleep(10000);
        }catch(InterruptedException exc){}
        myApp.stop();
        myApp.setPatternActive(firstPattern);
        myApp.play();
      //  myApp.offSampler();
    }
}

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
            Thread.sleep(Sampler.getDelay());
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
// Class-controller of app, singletone
class Sampler{

    // Singletone Realization

    private Sampler(){}
    private static class SamplerHolder{
        private final static Sampler instance = new Sampler();
    }
    public static Sampler getSampler(){
        return SamplerHolder.instance;
    }

    // Methods

    public void offSampler(){
        isPlaying = false;
    }

    public void play(){
        System.out.println("Начинаю воспроизведение...");
        setPlaying(true);
        activePattern.playPattern();}


    public void resume()
    {
        System.out.println("Возобновляю работу сэмплера");
        setPlaying(true);
        activePattern.resumePattern();
    }


    public void stop(){
        System.out.println("Trying to stop sampler!");
        setPlaying(false);
        activePattern.stopPattern();
    }
    public void pause(){}
    public void addPattern(Pattern patt){
        patterns.add(patt);
    }

    public void setPatternActive(Pattern pattern){
        this.activePattern = pattern;
    }

    public Pattern getActivePattern(){
        return this.activePattern;
    }

    static public int getDelay(){
        double dblBPM = (double) BPM;
        double delay =  120.0/dblBPM * 500.0;
        return (int) delay;

    }

    public void setReplays(int q){
        replays = q;
    }
    public static int getReplays(){
        return replays;
    }
    public static void setBPM(int B){
        BPM = B;
    }
    static public int getBPM(){
        return BPM;
    }
    public static void setSteps(int s){
        steps = s;
    }
    static public int getSteps(){
        return steps;
    }
    static public boolean isPlaying(){return isPlaying;}
    static public void setPlaying(boolean t){isPlaying = t;}
    public Pattern getPattern(int number){
        return patterns.get(number-1);
    }
    public int getCurrentStep(){
        return currentStep;
    }
    public void setCurrentStep(int step){
        currentStep = step;
    }

//PROPERTIES
    private ArrayList<Pattern> patterns = new ArrayList<>();
    private Pattern activePattern;
    private int currentStep;
    static private int BPM = 280;
    static private int steps = 16;
    static private int replays = 1;
    static private boolean isPlaying = false;

}
//CLASS Pattern, keeper of tracks
class Pattern {
    //METHODS
    //test
    public ArrayList<Track> getTracksArray(){
        return tracksArray;
    }
    //end test
    public Track getTrack(int number) {
        return tracksArray.get(number - 1);
    }
    public void addTrack(String name) {
        Track track = new Track(name);
        track.makeHits();
        tracksArray.add(track);
        Thread thread = track.getTrackThread();
        thread.setName("Track-" + trackCounter + track.getTrackThreadName() + "  Time: ");
       // track.getTrackThread().start();
        trackCounter++;
    }
    public void playPattern() {
        for (Track track : tracksArray) {
           if (track.getTrackThread().getState() == Thread.State.NEW ) {
                track.getTrackThread().start();}
            else track.requestResume(track);
            }

    }

    public void resumePattern(){
        for (Track track : tracksArray) {
            track.requestResume(track);
        }
    }

    public void stopPattern() {
        for (Track track : tracksArray) {
            if(track.getTrackThread().getState() == Thread.State.TIMED_WAITING || track.getTrackThread().getState() == Thread.State.RUNNABLE)
            {
                track.pause();
                System.out.println("Current thread: stopped " + track.getTrackThread().getName() + "interuptedVar = " + track);

            }
        }
    }
    //PROPERTIES
    private ArrayList<Track> tracksArray = new ArrayList<>(10);
    private int trackCounter = 1;
}

//CLASS track, keeper of HITS, and performer of hits running
 class Track implements Runnable {
     //METHODS
     //test
     public Instrument getConnectedInstrument(){
         return connectedInstrument;
     }
     //end test
     public Track(String n){
         t = new Thread(this);
         name = n;
         System.out.println("Новый поток: " + t) ;
         //t.start();
     }
     public Thread getTrackThread(){
         return this.t;
     }
     public String getTrackThreadName(){
         return this.name;
     }
     public void stop(){keepRunning = false;}
     public void pause() {
         isPaused = true;
     }
     public synchronized void requestResume(Track track){
         track.notify();
     }
     public ArrayList<Hit> getHits(){
         return hitsArray;
     }
    public void connectInstrument(String URL){
         this.connectedInstrument = new Instrument(URL);
     }
     public void makeHits(){
         for(int i=0;i<Sampler.getSteps();i++)
         {
             hitsArray.add(new Hit(false));
         }
     }
     public void makeHitActive(int... n){
         for(int i : n)
             hitsArray.get(i-1).setActive();
     }
    public void performSound(){
                for (Hit hit : hitsArray) {
                    //System.out.println("Проверяю играет ли сэмплер");
                    if(!isPaused) {
                        if (hit.getActive()) {
                            this.connectedInstrument.playSound();
                            System.out.println("Active hit, step number " + hitsArray.indexOf(hit));
                        } else {
                            try {
                                Thread.sleep(Sampler.getDelay());
                            } catch (InterruptedException exc) {}
                        }
                    }
                }
    }
     public void run() {
         keepRunning = true;
         try {
             while (keepRunning) {
                 if (isPaused) {
                     synchronized (this){
                     // System.out.println("Поступил запрос на стоп");
                         wait();
                         isPaused = false;
                     }
                 }
                  else performSound();
             }
         } catch (Exception e) {
             System.out.println(name + " прерван.");}
     }


     //PROPERTIES
     private String name;
     private Thread t;
     private boolean isPaused = false;
     private boolean keepRunning = false;
     private ArrayList<Hit> hitsArray = new ArrayList<>();
     private Instrument connectedInstrument = new Instrument("H2Sv4 - THHL - HiHat(0009).wav");
     //INNER CLASS
    public class Hit
    {
        //METHODS
        public Hit(boolean act){
            this.isActive = act;
        }
        public void setActive(){
            this.isActive = true;
        }
        public boolean getActive(){
            return isActive;
        }
        //PROPERTIES
        private boolean isActive = false;
    }
}

