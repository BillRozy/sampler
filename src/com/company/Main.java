package com.company;

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
        myApp.pause();
        try{Thread.sleep(2000);
        }catch(InterruptedException exc){}
        myApp.play();
        try{Thread.sleep(2000);
        }catch(InterruptedException exc){}
        myApp.stop();
        myApp.setPatternActive(secondPattern);
        try{Thread.sleep(1000);
        }catch(InterruptedException exc){}
        myApp.setBPM(140);
        myApp.play();
        try{Thread.sleep(3000);
        }catch(InterruptedException exc){}
        myApp.pause();
        myApp.setPatternActive(firstPattern);
        myApp.play();
        try{Thread.sleep(3000);
        }catch(InterruptedException exc){}
       myApp.stop();
      //  myApp.offSampler();
    }
}





