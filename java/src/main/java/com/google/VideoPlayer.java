package com.google;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  int counter = 0;
  String oldtitle = "";
  private static final String STOP = "stop";
  int stopCounter = 0;
  int pauseCounter =0;
  int continueCounter =0;
  ArrayList<String> unique = new ArrayList<>();
  ArrayList<String> Playlist1 = new ArrayList<>();


  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    var vids = videoLibrary.getVideos();
    vids.sort(Comparator.comparing(Video::getTitle));
  
    for (Video vid : vids) {
      vid.getTags().toString().replace(","," ");
      System.out.println(((Video) vid).getTitle() + " (" + vid.getVideoId() + ") " + vid.getTags().toString().replaceAll(",", ""));
    }

  }

  public void playVideo(String videoId) {
    var vids = videoLibrary.getVideo(videoId);

    if (vids == null) {
      System.out.println("Cannot play video: Video doos not exist");
      System.exit(0);
    }

    if (counter > 0) {

      stopVideo();
      System.out.println("Playing video: " + vids.getTitle());
    }
    if (counter == 0) {
      System.out.println("Playing video: " + vids.getTitle());
    }
    oldtitle = vids.getTitle();
    counter += 1;

  }

  public void stopVideo() {

    System.out.println("Stopping video: " + oldtitle);
    stopCounter += 1;
    if (stopCounter > 1) {
      System.out.println("Cannot stop video: No video is currently playing");
    }
  }

  public void playRandomVideo() {
    var vids = videoLibrary.getVideos();
    ArrayList<String> ID = new ArrayList<String>();
    for (Video vid : vids) {
      ID.add(((Video) vid).getVideoId());
    }
    Random rand = new Random();
    String randomElement = ID.get(rand.nextInt(ID.size()));
    playVideo(randomElement);
  }

  public void pauseVideo() {
    if(pauseCounter >1){
      System.out.println("Cannot pause video: No video is currently playing");
    }
    if(pauseCounter ==1 ){
      System.out.println("Video already paused: " + oldtitle);
      pauseCounter +=1;
      }
    if(pauseCounter ==0 ){
    System.out.println("Pausing video: " + oldtitle);
    pauseCounter +=1;
    }
 
    
  }

  public void continueVideo() {

    if(continueCounter >1){
      System.out.println("Cannot continue video: No video is currently playing");
    }
    else if(pauseCounter >=1){
      System.out.println("Continuing video: " + oldtitle);
     continueCounter+=1;
    }

    else if(pauseCounter <1){
      System.out.println("Cannot continue video: No video is currently playing");
      continueCounter +=1;
    }
  
  
   


  
  }

  public void showPlaying() {
    var vids = videoLibrary.getVideos();
    for(int x =0;x < vids.size(); x++){
      if(vids.get(x).getTitle() == oldtitle){
        if(stopCounter >1){
          System.out.println("No video is currently playing");
         
        }
       else if(pauseCounter !=1){
          System.out.println("Currently playing: "+ vids.get(x).getTitle() +  " ("+ vids.get(x).getVideoId() + ") " + vids.get(x).getTags().toString().replace(",",""));
        }
      else  if(pauseCounter == 1)
        System.out.println("Currently playing: " + vids.get(x).getTitle() +  " ("+ vids.get(x).getVideoId() + ") " + vids.get(x).getTags().toString().replace(",","") + " - PAUSED");
     
      }
    
    }
    
  }

  
  public void createPlaylist(String playlistName) {

    playlistName.trim();
    playlistName.replace(" ", "");

    String lowercase = playlistName.toLowerCase();

  
  

    unique.add(lowercase);

    int occurrences = Collections.frequency(unique, lowercase);

    if (occurrences >1 ){
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    }
    else{
      System.out.println("Successfully created new playlist: " + playlistName);

    }
    }




  public void addVideoToPlaylist(String playlistName, String videoId) {
    var vids = videoLibrary.getVideo(videoId);
    if(vids ==null){
     System.out.println("Cannot add video to my_playlist: Video does not exist");
     System.exit(0);

    }

  
      for(int y =0;y<Playlist1.size();y++){
        if(Playlist1.get(y).contains(vids.getTitle())){
          System.out.println("Cannot add video to " + playlistName + ": Video already added");
        
        
        }
        
        else{
          System.out.println("Added video to " + playlistName + ": " +  vids.getTitle());
          Playlist1.add(vids.getTitle());
        }
        {
         
    
        }
        
      }
      
      if(Playlist1.size() == 0){
        System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
      }
      

     
      for(int x =0;x<unique.size();x++){
        if(!unique.get(x).contains(playlistName)){
  System.out.println("Cannot add video to " + playlistName + " : Playlist does not exist");
  System.exit(0);
        }
      }
      
      //System.out.println("Added video to " + playlistName + ": " +  vids.getTitle());
   
        }
 
          
        
    

  

  public void showAllPlaylists() {
    if(unique.size()==0){
      System.out.println("No playlists exist yet");

    }

   if(unique.size()>0){
    System.out.println("Showing all playlists:");
    Collections.reverse(unique);
    for(int x =0;x<unique.size();x++){

  System.out.println(unique.get(x));
    }
   }
  }

  public void showPlaylist(String playlistName) {
  
    
for(int x = 0 ;x<Playlist1.size();x++){
    if(!unique.get(x).contains(playlistName)){
      System.out.println("Cannot show playlist " + playlistName + " : Playlist does not exist");
      System.exit(0);
    }
  }

System.out.println("Showing playlist: " + playlistName);
if(Playlist1.size()==0){
System.out.println("No videos here yet");
}

var vids = videoLibrary.getVideos();

for(int x = 0 ;x<Playlist1.size();x++){

    System.out.println(vids.get(x).getTitle() + " (" + vids.get(x).getVideoId() + ") " + vids.get(x).getTags().toString().replace(",", ""));

  }
}


  



  public void removeFromPlaylist(String playlistName, String videoId) {

    var vids = videoLibrary.getVideo(videoId);
    if (vids ==null){
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    }
    for(int x = 0 ;x<Playlist1.size();x++){
      if(!unique.get(x).contains(playlistName)){
        System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
        System.exit(0);
      }
    }

    for(int x = 0; x<Playlist1.size();x++){
if(Playlist1.get(x).contains( vids.getTitle())){
  Playlist1.remove(x);
  System.out.println("Removed video from " + playlistName+ vids.getTitle());


}
else{
  if(Playlist1.size() ==0){
    System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
  }
}
    }
  


    

}
    
  

  public void clearPlaylist(String playlistName) {
    for(int x =0;x<unique.size();x++){
      if(unique.get(x).contains(playlistName)){
   Playlist1.clear();
System.out.println("Successfully removed all videos from " + playlistName);
      }
      else{

    System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist ");
  }
}
}


  public void deletePlaylist(String playlistName) {
    if(unique.size() ==0 ){
      System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
    }
    for(int x =0;x<unique.size();x++){
      if(unique.get(x).contains(playlistName)){
   unique.remove(x);
System.out.println("Deleted Playlist: " + playlistName);
      }
     
       
      }
   
    }
   

  

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }

}