package com.company;

import java.util.ArrayList;

/**
 * Created by FD on 28.04.2016.
 */
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
