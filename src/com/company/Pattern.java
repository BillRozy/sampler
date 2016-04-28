package com.company;

import java.util.ArrayList;

/**
 * Created by FD on 28.04.2016.
 */
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
