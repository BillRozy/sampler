package com.company;

import java.util.ArrayList;

/**
 * Created by FD on 28.04.2016.
 */
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
                        System.out.println("Поступил запрос на стоп");
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

